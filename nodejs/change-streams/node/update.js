const MongoClient = require('mongodb');
const url = require('./config.js').mongoDBUrl;

const update = {
  $set: { 'celsiusTemperature': 23},
  $currentDate: { 'timeStamp': true }
};

MongoClient.connect(url, (err, client) => {
  const coll = client.db("demo").collection("devices");
  coll.update({"name": "ecobee_1234"}, update, { multi: true}).then(() => client.close());
});