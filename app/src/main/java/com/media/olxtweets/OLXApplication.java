package com.media.olxtweets;

import android.app.Application;
import android.content.Context;

import com.media.olxtweets.di.Injector;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by Samih on 24-Dec-17.
 */

public class OLXApplication extends Application {

    private static OLXApplication app;
    private Injector injector;

    public static OLXApplication get(Context context) {
        return (OLXApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterConfig config = new TwitterConfig.Builder(this)
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .build();
        Twitter.initialize(config);
        app = this;
        initInjector();
    }


    private void initInjector() {
        injector = new Injector();
    }

    public Injector getInjector() {
        return injector;
    }
}


