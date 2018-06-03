const MongoClient = require("mongodb");
const storage = require("node-persist");
const EJSON = require("mongodb-extjson");
const url = require("./config.js").mongoDBUrl;

const matchStage = { $match: { "fullDocument.celsiusTemperature": { $gt: 15 } } };

let options = {
  fullDocument: "updateLookup"
};

const CS_TOKEN = "changeStreamResumeToken";

MongoClient.connect(url, (err, client) => {
  if (err) {
    console.warn(err);
    return;
  }

  const coll = client.db("demo").collection("devices");
  let changeStream = coll.watch([matchStage], options);

  storage.init({ dir: "localStorage" }).then(() => {
    storage
      .getItem(CS_TOKEN)
      .then(
      token => {
        if (token !== undefined) {
          console.log(`using resume token: ${token}`);
          options.resumeAfter = EJSON.parse(token);
          changeStream = coll.watch([matchStage], options);
        }
      },
      err => {
        console.log("error retrieving change stream resume token: " + err);
      }
      )
      .then(() => {
        console.log("polling change stream...");
        pollStream(changeStream, storage);
      });
  });
});

function pollStream(cs, storage) {
  console.log("waiting for change stream...");
  cs.next((err, change) => {
    if (err) return console.log(err);
    resumeToken = EJSON.stringify(change._id);
    storage.setItem(CS_TOKEN, resumeToken);
    console.log(change);
    pollStream(cs, storage);
  });
}

