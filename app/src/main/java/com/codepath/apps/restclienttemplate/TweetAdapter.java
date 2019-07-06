package com.codepath.apps.restclienttemplate;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author      Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: TweetAdapter for Twitter
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    // Instance Fields:
    private List<Tweet> mtweets;
    Context context;
    private TwitterClient mClient;

    // Pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets, TwitterClient client){
        mtweets = tweets;
        mClient = client;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // for each row, inflate the layout and cache references into ViewHolder
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        // Bind the values based on the position of the element
        final Tweet tweet = mtweets.get(position);  // Get the data according to the position
        // Populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTime.setText(getRelativeTimeAgo(tweet.createdAt));
        if(tweet.isRetweeted){
            holder.retweet.setImageResource(R.drawable.ic_vector_retweet);
        } else {
            holder.retweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
        }
        if(tweet.isFavorited){
            holder.fav.setImageResource(R.drawable.ic_vector_like_heart);

        } else {
            holder.fav.setImageResource(R.drawable.ic_vector_heart_stroke);

        }

        // Favorite
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tweet.isFavorited){
                    mClient.unFavoriteTweet(tweet.uid, new JsonHttpResponseHandler());
                    holder.fav.setImageResource(R.drawable.ic_vector_heart_stroke);
                    tweet.favCount--;
                    tweet.isFavorited = false;
                    holder.tvFav.setText(Integer.toString(tweet.favCount));
                } else {
                    mClient.favoriteTweet(tweet.uid, new JsonHttpResponseHandler());
                    tweet.isFavorited = true;
                    tweet.favCount++;
                    holder.fav.setImageResource(R.drawable.ic_vector_like_heart);
                    holder.tvFav.setText(Integer.toString(tweet.favCount));
                }

            }
        });
        // Retweet
        holder.retweet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(tweet.isRetweeted){
                    mClient.unRetweet(tweet.uid, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.d("", response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                    tweet.isRetweeted = false;
                    holder.retweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
                    tweet.retweetCount--;
                    holder.tvRetweet.setText(Integer.toString(tweet.retweetCount));
                } else {
                    mClient.retweet(tweet.uid, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.d("", response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                    holder.retweet.setImageResource(R.drawable.ic_vector_retweet);
                    tweet.retweetCount++;
                    tweet.isRetweeted = true;
                    holder.tvRetweet.setText(Integer.toString(tweet.retweetCount));
                }
            }
        });

        holder.tvRetweet.setText(Integer.toString(tweet.retweetCount));
        holder.tvFav.setText(Integer.toString(tweet.favCount));
        int radius = 15; // corner radius, higher value = more rounded
        int margin = 0; // crop margin, set to 0 for corners with no crop
        Glide.with(context).load(tweet.user.profileImageUrl).bitmapTransform(new RoundedCornersTransformation(context, radius, margin))
                .into(holder.ivProfileImage);
        Glide.with(context).load(tweet.mediaUrl).into(holder.tvMedia);
    }

    // Clean all elements of the recycler
    public void clear() {
        mtweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mtweets.addAll(list);
        notifyDataSetChanged();
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @Override
    public int getItemCount(){
        return mtweets.size();
    }

    // ViewHolder class for RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Attributes:
        public ImageView ivProfileImage;  // Profile Image of User
        public TextView tvUsername;       // Username
        public TextView tvBody;           // Body of Tweet
        public TextView tvTime;           // Timestamp of Tweet
        public TextView tvRetweet;        // Retweet Count
        public TextView tvFav;            // Fav Count
        public ImageView tvMedia;         // Media Image
        public ImageButton retweet;
        public ImageButton fav;

        public ViewHolder(View itemView){
            super(itemView);
            ivProfileImage = ButterKnife.findById(itemView, R.id.ivProfileImage);
            tvUsername = ButterKnife.findById(itemView, R.id.tvUserName);
            tvBody = ButterKnife.findById(itemView, R.id.tvBody);
            tvTime = ButterKnife.findById(itemView, R.id.tvTime);
            tvRetweet = ButterKnife.findById(itemView, R.id.retweetCount);
            tvFav = ButterKnife.findById(itemView, R.id.favCount);
            tvMedia = ButterKnife.findById(itemView, R.id.tvMedia);
            retweet = ButterKnife.findById(itemView,R.id.retweetBtn);
            fav = ButterKnife.findById(itemView, R.id.favBtn);
        }
    }
}
