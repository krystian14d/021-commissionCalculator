// creating database and collections:
db = new Mongo().getDB("commissionCalculator");
db.createCollection("transactions", {capped: false});
db.createCollection("fees", {capped: false});
db.createCollection("commissionRequestsLog", {capped: false});