package com.media.olxtweets.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;

import com.media.olxtweets.OLXApplication;
import com.media.olxtweets.R;
import com.media.olxtweets.data.TwitterClient;
import com.media.olxtweets.di.Injector;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class OLXTabsActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private String screenName;
    private Toolbar toolbar;
    private boolean isRTL = false;

    public static final String HOME_TAG = "HOME";
    public static final String EGYPT_TAG = "olxegypt";
    public static final String DEV_TAG = "AndroidDev";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    attachFragment(transaction, HOME_TAG, screenName);
                    break;
                case R.id.navigation_egypt:
                    attachFragment(transaction, EGYPT_TAG, EGYPT_TAG);
                    break;
                case R.id.navigation_dev:
                    attachFragment(transaction, DEV_TAG, DEV_TAG);
                    break;
            }

            Fragment curFrag = fragmentManager.getPrimaryNavigationFragment();
            if (curFrag != null) {
                transaction.detach(curFrag);
            }

            transaction.setPrimaryNavigationFragment(fragment);
            transaction.setReorderingAllowed(true);
            transaction.commitNowAllowingStateLoss();
            return true;
        }
    };


    public Fragment attachFragment(FragmentTransaction transaction, String tag, String data){
        fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment == null){
            fragment = TweetsFragment.newInstance(data);
            transaction.add(R.id.main_container, fragment, tag);
        }else{
            transaction.attach(fragment);
        }
        return fragment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olxtabs);

        fragmentManager = getSupportFragmentManager();
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        getWindow().getDecorView().setTextDirection(View.TEXT_DIRECTION_LTR);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_tab_actions);
        toolbar.setOnMenuItemClickListener(this);
        screenName = TwitterCore.getInstance().getSessionManager().getActiveSession().getUserName();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logoutUser();
                return true;

            case R.id.rtl:
                setLayoutDirection(item);
                return true;
        }
        return false;
    }

    void setLayoutDirection(MenuItem item){
        if(isRTL){
            getWindow().getDecorView().setTextDirection(View.TEXT_DIRECTION_LTR);
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            item.setIcon(getDrawable(R.drawable.ic_action_ar));
            isRTL = false;
        }else{
            getWindow().getDecorView().setTextDirection(View.TEXT_DIRECTION_RTL);
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            item.setIcon(getDrawable(R.drawable.ic_action_en));
            isRTL = true;
        }
    }


    public void logoutUser() {
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
        Intent intent = new Intent(OLXTabsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
