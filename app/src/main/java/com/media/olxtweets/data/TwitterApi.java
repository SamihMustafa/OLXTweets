package com.media.olxtweets.data;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Samih on 25-Dec-17.
 */

public interface TwitterApi {

    @GET("1.1/statuses/user_timeline.json")
    Call<List<Tweet>> getUserTimeline(@Query("screen_name") String userId);


    @GET("oauth/authorize?force_login=true")
    Call<ResponseBody> loginUser(@Query("oauth_token") String token);

}
