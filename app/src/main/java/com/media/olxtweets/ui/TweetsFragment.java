package com.media.olxtweets.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.media.olxtweets.OLXApplication;
import com.media.olxtweets.R;
import com.media.olxtweets.di.Injector;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.List;

public class TweetsFragment extends Fragment implements TweetsContract.View, TweetClickListener {

    public String screenName;
    private Injector injector;
    private MyTweetsRecyclerViewAdapter adapter;
    public static final String NAME = "NAME";
    private TweetsPresenter presenter;
    private View view;

    public TweetsFragment() {
    }

    public static TweetsFragment newInstance(String name) {
        TweetsFragment fragment = new TweetsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NAME,name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        injector = OLXApplication.get(view.getContext()).getInjector();
        screenName = getArguments().getString(NAME);
        setupRecycler(view);
        presenter = new TweetsPresenter(this, screenName, injector.getTwitterApi());
        presenter.loadTimeline();

        return view;
    }

    private void setupRecycler(View view) {

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    layoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
            recyclerView.setLayoutManager(layoutManager);

            adapter = new MyTweetsRecyclerViewAdapter(this);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void loadTimeLine(List<Tweet> data) {
        adapter.setItems(data);
    }

    @Override
    public void onTweetClicked(Tweet tweet) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        ConstraintLayout view = (ConstraintLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_tweet, null);
        view.addView(new TweetView(view.getContext(),tweet));
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
