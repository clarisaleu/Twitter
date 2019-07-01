package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {

    // Attributes:
    public String body;
    public  long uid;  // database ID for the tweet
    public String createdAt;
    public User user;

    // Deserialize the JSON:
    public static Tweet fromJSON(JSONObject obj) throws JSONException {
        Tweet tweet = new Tweet();
        // Extract the values from the JSON
         tweet.body = obj.getString("text");
         tweet.uid = obj.getLong("id");
         tweet.createdAt = obj.getString("created_at");
         tweet.user = User.fromJSON(obj.getJSONObject("user"));
         return tweet;
    }
}
