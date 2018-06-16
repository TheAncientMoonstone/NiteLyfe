package com.lyfeforce.tim.nitelyfe;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.lyfeforce.tim.nitelyfe.AWS.AWSLoginHandler;
import com.lyfeforce.tim.nitelyfe.AWS.AWSLoginModel;

import static java.sql.DriverManager.println;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener, AWSLoginHandler {

    // AWS Logic
    AWSLoginModel awsLoginModel;

    // UI References
    EditText UsernameField = (EditText) findViewById(R.id.UsernameField);
    EditText PasswordField = (EditText) findViewById(R.id.PasswordField);
    Button SignInButton = (Button) findViewById(R.id.SignInButton);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        // Prints this line to make sure that everything is working properly.
        println("Authentication Activity is loading...");

        // Instantiate AWSLoginModel
        awsLoginModel = new AWSLoginModel(this, this);

        findViewById(R.id.SignInButton).setOnClickListener(this);

/*
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {

               SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(AuthenticationActivity.this, SignInUI.class);
                signin.login(AuthenticationActivity.this, MainActivity.class).execute();

            }
        }).execute();
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*
            case R.id.registerButton:
            registerAction();
            break;
            */
            /*
            case R.id.confirmButton:
            confirmAction();
            break;
            */
            case R.id.SignInButton:
                loginAction();
                break;
        }
    }

    private void loginAction() {
        // Handles the user authentication.
        // Authenticate the user and handle UI flow through the app.
        awsLoginModel.signInUser(UsernameField.getText().toString(), PasswordField.getText().toString());
    }

    @Override
    public void onRegisterSuccess(boolean mustConfirmToComplete) {
        if (mustConfirmToComplete) {
            Toast.makeText(AuthenticationActivity.this, "Almost done! Confirm code to complete registration", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AuthenticationActivity.this, "Registered! Log in!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRegisterConfirmed() {
        Toast.makeText(AuthenticationActivity.this, "Registered! Log in!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignInSuccess() {
        AuthenticationActivity.this.startActivity(new Intent(AuthenticationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void onFailure(int process, Exception exception) {
        exception.printStackTrace();
        String whatProcess = "";
        switch (process) {
            case AWSLoginModel.PROCESS_SIGN_IN:
                whatProcess = "Sign In:";
                break;
            case AWSLoginModel.PROCESS_REGISTER:
                whatProcess = "Register:";
                break;
                case AWSLoginModel.PROCESS_CONFIRM_REGISTRATION:
                    whatProcess = "Registration Confirmation:";
                    break;
        }
        Toast.makeText(AuthenticationActivity.this, whatProcess + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
