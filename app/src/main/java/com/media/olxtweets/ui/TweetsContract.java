package com.media.olxtweets.ui;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by Samih on 25-Dec-17.
 */

public interface TweetsContract {

    interface Presenter{

        void loadTimeline();
    }

    interface View{

        void loadTimeLine(List<Tweet> data);
    }

}
