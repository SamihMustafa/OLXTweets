package com.media.olxtweets.di;

import android.support.annotation.Nullable;

import com.media.olxtweets.data.TwitterApi;
import com.media.olxtweets.data.TwitterClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Samih on 25-Dec-17.
 */

public class Injector {

    @Nullable
    private TwitterClient client;

    public TwitterApi getTwitterApi(){
        if(client == null){
            client = new TwitterClient(getTwitterSession());
        }
        return client.getService();
    }

    public TwitterSession getTwitterSession() {
        return TwitterCore.getInstance().getSessionManager().getActiveSession();
    }
}
