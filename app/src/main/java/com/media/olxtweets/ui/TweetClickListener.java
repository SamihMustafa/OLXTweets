package com.media.olxtweets.ui;

import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by Samih on 25-Dec-17.
 */

public interface TweetClickListener {
    void onTweetClicked(Tweet tweet);
}
