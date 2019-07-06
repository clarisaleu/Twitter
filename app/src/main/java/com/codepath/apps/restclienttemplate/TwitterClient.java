package com.codepath.apps.restclienttemplate;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author      Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: Twitter Client - responsible for communicating with Twitter API.
 */
public class TwitterClient extends OAuthBaseClient {
	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); // Change this

	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	//public static final String REST_CONSUMER_KEY = "4KxocRp2Wh8RZ9cy1KJEjxGVy";       // Change this
	//public static final String REST_CONSUMER_SECRET = "EeyJ4vEZN3al7c0C13bMwAY3pGc2RASrampYtvJvnX1kLDHKJf"; // Change this

	public static final String REST_CONSUMER_KEY = "rllBCzpDlSKxcP4KLfYaHIqtV";
	public static final String REST_CONSUMER_SECRET = "kybWvvX9paFvOiUjNG7S8Mgexi2XVMp37eisBRfUfUtkXFpT3z";

	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

	// See https://developer.chrome.com/multidevice/android/intents
	public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

	public TwitterClient(Context context) {
		super(context, REST_API_INSTANCE,
				REST_URL,
				REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET,
				String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
						context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
	}

	public void getInterestingnessList(long max_id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25 );

		if(max_id != 0) {
			params.put("max_id", max_id);
		}

		//params.put("since_id",  1);
		client.get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
	public void sendTweet(String message, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("status", message);
		client.post(apiUrl, params, handler);
	}

	public void unFavoriteTweet(long uid, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("favorites/destroy.json");
		RequestParams params = new RequestParams();
		params.put("id",uid);
		client.post(url, params, handler);
	}

	public void favoriteTweet(long uid, AsyncHttpResponseHandler handler) {
		String url =getApiUrl("favorites/create.json");
		RequestParams params = new RequestParams();
		params.put("id",uid);
		client.post(url, params, handler);
	}


	public void unRetweet(long uid, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/unretweet/"+String.format("%s",uid)+".json");
		RequestParams params = new RequestParams();
		params.put("id",uid);
		client.post(url, params, handler);
	}

	public void retweet(long uid, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/retweet/"+String.format("%s",uid)+".json");
		RequestParams params = new RequestParams();
		params.put("id",uid);
		client.post(url, params, handler);
	}
}
