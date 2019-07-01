package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User  {

    // Attributes:
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    // Deserialize the JSON
    public static User fromJSON(JSONObject obj) throws JSONException {
        User user = new User();
        user.name = obj.getString("name");
        user.uid = obj.getLong("id");
        user.screenName = obj.getString("screen_name");
        user.profileImageUrl = obj.getString("profile_image_url");
        return user;
    }
}
