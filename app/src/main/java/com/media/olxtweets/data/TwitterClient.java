package com.media.olxtweets.data;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Samih on 25-Dec-17.
 */

public class TwitterClient extends TwitterApiClient {

    public TwitterClient(TwitterSession session) {
        super(session);
    }

    public TwitterApi getService(){
        return getService(TwitterApi.class);
    }

}
