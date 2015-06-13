
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
  console.log("afterSave on a Timer !");
  query = new Parse.Query("Post");
  query.get(request.object.get("post").id, {
    success: function(post) {
      post.increment("comments");
      post.save();
    },
    error: function(error) {
      console.error("Got an error " + error.code + " : " + error.message);
    }
  });
});