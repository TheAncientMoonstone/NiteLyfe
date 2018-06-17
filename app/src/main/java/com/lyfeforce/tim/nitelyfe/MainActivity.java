package com.lyfeforce.tim.nitelyfe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lyfeforce.tim.nitelyfe.AWS.AWSLoginModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
     * This the landing page when the user has successfully logged into the app.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String who = AWSLoginModel.getSavedUserName(MainActivity.this);

        TextView hello = findViewById(R.id.hello);
        hello.setText("Hello" + who + "!");
    }

    // Stops the user from going back to the splash screen.
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoutButton:
                logoutAction();
                break;
        }
    }

    private void logoutAction() {
        AWSLoginModel.doUserLogout();
        startActivity(new Intent(MainActivity.this, AuthenticationActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
