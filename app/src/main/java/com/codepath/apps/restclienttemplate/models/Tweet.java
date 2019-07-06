package com.codepath.apps.restclienttemplate.models;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author      Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: Timeline for Twitter
 */
//@Parcel  // Annotation indicate class is Parcelable
public class Tweet implements Parcelable {
    // Attributes:
    public String body;
    public  long uid;  // database ID for the tweet
    public String createdAt;
    public int retweetCount;
    public int favCount;
    public User user;
    public String mediaUrl;
    public boolean isFavorited;  // already favorited
    public boolean isRetweeted;  // already retweeted


    // Default Constructor
    public Tweet() { }

    protected Tweet(android.os.Parcel in) {
        body = in.readString();
        uid = in.readLong();
        createdAt = in.readString();
        retweetCount = in.readInt();
        favCount = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        mediaUrl = in.readString();
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(android.os.Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };


    public static Tweet fromJSON(JSONObject obj) throws JSONException {
        Tweet tweet = new Tweet();
        // Extract the values from the JSON
         tweet.body = obj.getString("text");
         tweet.uid = obj.getLong("id");
         tweet.createdAt = obj.getString("created_at");
         tweet.retweetCount = obj.getInt("retweet_count");
         tweet.favCount = obj.getInt("favorite_count");
         tweet.user = User.fromJSON(obj.getJSONObject("user"));
         try {
             tweet.mediaUrl = obj.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
         } catch (JSONException e){
             tweet.mediaUrl = null;
        }
         tweet.isFavorited = obj.getBoolean("favorited");
         tweet.isRetweeted = obj.getBoolean("retweeted");
         return tweet;
    }

    // Return size of body
    @Override
    public int describeContents() {
        return body.length();
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeLong(uid);
        dest.writeString(createdAt);
        dest.writeInt(retweetCount);
        dest.writeInt(favCount);
        dest.writeParcelable(user, flags);
        dest.writeString(mediaUrl);
    }
}
