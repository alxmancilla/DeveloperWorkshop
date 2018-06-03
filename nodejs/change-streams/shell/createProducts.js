cnxString = "mongodb://localhost:27017/demo?replicaSet=rs";
conn = new Mongo(cnxString);
db = conn.getDB("demo");
collection = db.stock;

var docToInsert = {
  name: "pineapple",
  quantity: 10
};

function sleepFor(sleepDuration) {
  var now = new Date().getTime();
  while (new Date().getTime() < now + sleepDuration) {
    /* do nothing */
  }
}

function create() {
  sleepFor(1000);
  print("inserting doc...");
  //uncomment the line below
  //docToInsert.quantity = 11 + Math.floor(Math.random() * 10);
  res = collection.insert(docToInsert);
  print(res)
}

while (true) {
  create();
}
