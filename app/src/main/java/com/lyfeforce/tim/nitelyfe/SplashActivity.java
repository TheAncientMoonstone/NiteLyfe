package com.lyfeforce.tim.nitelyfe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class SplashActivity extends AppCompatActivity {

    /*
     * This is used to load the app when the user has already logged into the app.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Calls the AWSMobileClient and initialises it in the app.
        AWSMobileClient.getInstance().initialize(SplashActivity.this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
                identityManager.resumeSession(SplashActivity.this, new StartupAuthResultHandler() {
                    @Override
                    public void onComplete(StartupAuthResult authResults) {
                        if (authResults.isUserSignedIn()) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }
                }, 300);
            }
        }).execute();
    }
}
