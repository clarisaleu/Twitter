package com.codepath.apps.restclienttemplate.models;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

//@Parcel  // annotation indicate class is Parcelable
public class User implements Parcelable {

    // Attributes:
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    // Default Constructor
    public User() { }

    protected User(android.os.Parcel in) {
        name = in.readString();
        uid = in.readLong();
        screenName = in.readString();
        profileImageUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(android.os.Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // Deserialize the JSON
    public static User fromJSON(JSONObject obj) throws JSONException {
        User user = new User();
        user.name = obj.getString("name");
        user.uid = obj.getLong("id");
        user.screenName = obj.getString("screen_name");
        user.profileImageUrl = obj.getString("profile_image_url");
        return user;
    }

    @Override
    public int describeContents() {
        return screenName.length();
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(uid);
        dest.writeString(screenName);
        dest.writeString(profileImageUrl);
    }
}
