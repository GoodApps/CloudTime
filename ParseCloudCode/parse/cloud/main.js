
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});

//Parse.Cloud.define("afterSave", function(request, response) {
//  response.success("Hello world -- afterSave!");
//});


Parse.Cloud.afterSave("Timer", function(request) {
  // from https://www.parse.com/docs/js/guide#cloud-code
  console.log("afterSave on a Timer -- with push !");

  console.log("Before Parse.Push.send");
  var query = new Parse.Query(Parse.Installation);

  // query.equalTo('gender', 'male');
  // query.greaterThanOrEqualTo('age', 18);


  // http://blog.parse.com/announcements/pushing-from-the-javascript-sdk-and-cloud-code/ :
  Parse.Push.send({
    where: query, // Set our Installation query
//    channels: [ "Mr.T" ],
    data: {
       alert: "afterSave on a Timer -- Parse.Push.send"
    }
  });

  console.log("After Parse.Push.send");
//  query = new Parse.Query("Post");
//  query.get(request.object.get("post").id, {
//    success: function(post) {
//      post.increment("comments");
//      post.save();
//    },
//    error: function(error) {
//      console.error("Got an error " + error.code + " : " + error.message);
//    }
//  });
});


// 2 empty lines for Maciej to have the end visible ;)

