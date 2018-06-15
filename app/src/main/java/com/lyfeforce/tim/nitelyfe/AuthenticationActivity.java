package com.lyfeforce.tim.nitelyfe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class AuthenticationActivity extends AppCompatActivity {

    /**
     * VARIABLE AND OBJECT DECLARATIONS GO BELOW THIS TITLE
     **/
    EditText UsernameField = (EditText) findViewById(R.id.UsernameField);
    EditText PasswordField = (EditText) findViewById(R.id.PasswordField);
    private String Username;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                /*
               SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(AuthenticationActivity.this, SignInUI.class);
                signin.login(AuthenticationActivity.this, MainActivity.class).execute();
                */
            }
        }).execute();
    }

    // Verifies that the user has typed their username into the username field.
    public void verifyUsernameCompletion() {

        Username = UsernameField.getText().toString();
        if (Username.matches("")) {
            Toast.makeText(this,"Please enter your username", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    // This function will verify that the user has typed their password in the password field.
    public void verifyPasswordCompletion() {
        Password = PasswordField.getText().toString();
        if (Password.matches("")) {
            Toast.makeText(this,"Please entre your username", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
