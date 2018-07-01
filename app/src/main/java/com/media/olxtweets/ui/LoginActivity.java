package com.media.olxtweets.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.media.olxtweets.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    private TwitterLoginButton loginButton;
    private TwitterSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);

        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if(session != null){
            loginSuccess();
        }

       loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API call
                loginSuccess();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });

    }

    public void loginSuccess(){
        Intent nextActivity = new Intent(this, OLXTabsActivity.class);
        startActivity(nextActivity);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.

    }
}

