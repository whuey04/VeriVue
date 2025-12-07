// ----------------------------
// Collection structure for ap_associate_words
// ----------------------------
db.getCollection("ap_associate_words").drop();
db.createCollection("ap_associate_words");

// ----------------------------
// Documents of ap_associate_words
// ----------------------------
db.getCollection("ap_associate_words").insert([ {
    _id: ObjectId("60bdbfc1f2c2745b46b7f4a1"),
    associateWords: "Java",
    createdTime: ISODate("2025-08-29T06:42:09Z"),
    _class: "com.verivue.mongo.pojo.ApAssociateWords"
} ]);

// ----------------------------
// Collection structure for ap_user_search
// ----------------------------
db.getCollection("ap_user_search").drop();
db.createCollection("ap_user_search");

// ----------------------------
// Documents of ap_user_search
// ----------------------------
db.getCollection("ap_user_search").insert([ {
    _id: ObjectId("68b7774f27f9b467c98010a9"),
    userId: NumberInt("4"),
    keyword: "Test",
    createdTime: ISODate("2025-08-29T07:03:35Z"),
    _class: "com.verivue.search.pojos.ApUserSearch"
} ]);