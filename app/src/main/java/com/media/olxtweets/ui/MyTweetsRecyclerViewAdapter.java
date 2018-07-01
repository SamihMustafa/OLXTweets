package com.media.olxtweets.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.media.olxtweets.R;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;


public class MyTweetsRecyclerViewAdapter extends RecyclerView.Adapter<MyTweetsRecyclerViewAdapter.ViewHolder> {

    private final TweetClickListener listener;
    private List<Tweet> items = new ArrayList<>(0);

    public MyTweetsRecyclerViewAdapter(TweetClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Tweet> data) {
        this.items = data;
        notifyDataSetChanged();
    }

    @Override
    public MyTweetsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tweets, parent, false));
    }

    @Override
    public void onBindViewHolder(MyTweetsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tweetName;
        private final TextView tweetId;
        private final TextView tweetMessage;

        public ViewHolder(View view) {
            super(view);
            tweetName = view.findViewById(R.id.screenName);
            tweetId = view.findViewById(R.id.userId);
            tweetMessage = view.findViewById(R.id.tweet_message);
        }

        public void bind(final Tweet tweetdata, final TweetClickListener listener){
            tweetName.setText(tweetdata.user.screenName);
            tweetId.setText("@" + tweetdata.user.name);
            tweetMessage.setText(tweetdata.text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTweetClicked(tweetdata);
                }
            });
        }
    }
}
