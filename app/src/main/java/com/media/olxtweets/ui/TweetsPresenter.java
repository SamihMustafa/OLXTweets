package com.media.olxtweets.ui;

import android.app.AlertDialog;

import com.media.olxtweets.data.TwitterApi;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Samih on 25-Dec-17.
 */

public class TweetsPresenter implements TweetsContract.Presenter {

    private String screenName;
    private final TwitterApi client;
    private final WeakReference<TweetsContract.View> view;

    public TweetsPresenter(TweetsContract.View view, String screenName, TwitterApi client){
        this.view = new WeakReference<>(view);
        this.screenName = screenName;
        this.client = client;
    }

    @Override
    public void loadTimeline() {
        Call<List<Tweet>> data = client.getUserTimeline(screenName);
        data.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                setItems(result);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    private void setItems(Result<List<Tweet>> result){
        if (view.get() == null) {
            return;
        }
        view.get().loadTimeLine(result.data);
    }
}
