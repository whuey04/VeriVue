// ----------------------------
// Collection structure for ap_comment
// ----------------------------
db.getCollection("ap_comment").drop();
db.createCollection("ap_comment");

// ----------------------------
// Documents of ap_comment
// ----------------------------
db.getCollection("ap_comment").insert([ {
    _id: ObjectId("68a7b521e06cf510f19e1e1a"),
    authorId: NumberInt("4"),
    authorName: "admin",
    entryId: NumberLong("1956535456368136194"),
    type: NumberInt("0"),
    content: "hi",
    likes: NumberInt("0"),
    reply: NumberInt("0"),
    flag: NumberInt("0"),
    createdTime: ISODate("2025-08-22T00:09:05Z"),
    _class: "com.verivue.model.comment.pojo.ApComment"
} ]);
db.getCollection("ap_comment").insert([ {
    _id: ObjectId("68a8030a5f7be6478a7a5ff2"),
    authorId: NumberInt("4"),
    authorName: "admin",
    entryId: NumberLong("1958761974842392577"),
    type: NumberInt("0"),
    content: "喜欢原神！",
    likes: NumberInt("3"),
    reply: NumberInt("2"),
    flag: NumberInt("0"),
    createdTime: ISODate("2025-08-22T05:41:30Z"),
    _class: "com.verivue.model.comment.pojo.ApComment"
} ]);
db.getCollection("ap_comment").insert([ {
    _id: ObjectId("68aabf292ec542601d17160b"),
    authorId: NumberInt("4"),
    authorName: "admin",
    entryId: NumberLong("1958761974842392577"),
    type: NumberInt("0"),
    content: "Hello",
    likes: NumberInt("0"),
    reply: NumberInt("0"),
    flag: NumberInt("0"),
    createdTime: ISODate("2025-08-24T07:28:41Z"),
    _class: "com.verivue.model.comment.pojo.ApComment"
} ]);

// ----------------------------
// Collection structure for ap_comment_like
// ----------------------------
db.getCollection("ap_comment_like").drop();
db.createCollection("ap_comment_like");

// ----------------------------
// Documents of ap_comment_like
// ----------------------------
db.getCollection("ap_comment_like").insert([ {
    _id: ObjectId("68aba41474b60c6ec7f7dc21"),
    authorId: NumberInt("4"),
    commentId: "68a8030a5f7be6478a7a5ff2",
    _class: "com.verivue.model.comment.pojo.ApCommentLike"
} ]);
db.getCollection("ap_comment_like").insert([ {
    _id: ObjectId("68abb5acd6153615c8d51e63"),
    authorId: NumberInt("4"),
    commentId: "68aba40474b60c6ec7f7dc20",
    _class: "com.verivue.model.comment.pojo.ApCommentLike"
} ]);

// ----------------------------
// Collection structure for ap_comment_reply
// ----------------------------
db.getCollection("ap_comment_reply").drop();
db.createCollection("ap_comment_reply");

// ----------------------------
// Documents of ap_comment_reply
// ----------------------------
db.getCollection("ap_comment_reply").insert([ {
    _id: ObjectId("68a7b568e06cf510f19e1e1b"),
    authorId: NumberInt("4"),
    authorName: "admin",
    commentId: "68a4b49a82d70f4a60884cc4",
    content: "test",
    likes: NumberInt("0"),
    createdTime: ISODate("2025-08-22T00:10:16Z"),
    updatedTime: ISODate("2025-08-22T00:10:16Z"),
    _class: "com.verivue.model.comment.pojo.ApCommentReply"
} ]);
db.getCollection("ap_comment_reply").insert([ {
    _id: ObjectId("68a8032a5f7be6478a7a5ff4"),
    authorId: NumberInt("4"),
    authorName: "admin",
    commentId: "68a8030a5f7be6478a7a5ff2",
    content: "原来你也www",
    likes: NumberInt("2"),
    createdTime: ISODate("2025-08-22T05:42:02Z"),
    updatedTime: ISODate("2025-08-22T05:42:02Z"),
    _class: "com.verivue.model.comment.pojo.ApCommentReply"
} ]);
db.getCollection("ap_comment_reply").insert([ {
    _id: ObjectId("68aac4cb63d52c10a3e9a37c"),
    authorId: NumberInt("4"),
    authorName: "admin",
    commentId: "68a8030a5f7be6478a7a5ff2",
    content: "Goooooood!",
    likes: NumberInt("0"),
    createdTime: ISODate("2025-08-24T07:52:43Z"),
    updatedTime: ISODate("2025-08-24T07:52:43Z"),
    _class: "com.verivue.model.comment.pojo.ApCommentReply"
} ]);

// ----------------------------
// Collection structure for ap_comment_reply_like
// ----------------------------
db.getCollection("ap_comment_reply_like").drop();
db.createCollection("ap_comment_reply_like");

// ----------------------------
// Documents of ap_comment_reply_like
// ----------------------------
db.getCollection("ap_comment_reply_like").insert([ {
    _id: ObjectId("68a8032c5f7be6478a7a5ff5"),
    authorId: NumberInt("4"),
    commentReplyId: "68a8032a5f7be6478a7a5ff4",
    _class: "com.verivue.model.comment.pojo.ApCommentReplyLike"
} ]);
db.getCollection("ap_comment_reply_like").insert([ {
    _id: ObjectId("68aac5414b87df2d7c6271b8"),
    authorId: NumberInt("4"),
    commentReplyId: "68a8032a5f7be6478a7a5ff4",
    _class: "com.verivue.model.comment.pojo.ApCommentReplyLike"
} ]);
