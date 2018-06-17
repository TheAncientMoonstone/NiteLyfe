package com.lyfeforce.tim.nitelyfe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lyfeforce.tim.nitelyfe.AWS.AWSLoginModel;

public class MainActivity extends AppCompatActivity {

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
}
