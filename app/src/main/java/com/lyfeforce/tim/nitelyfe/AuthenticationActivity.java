package com.lyfeforce.tim.nitelyfe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.lyfeforce.tim.nitelyfe.AWS.AWSLoginHandler;
import com.lyfeforce.tim.nitelyfe.AWS.AWSLoginModel;

import static java.sql.DriverManager.println;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener, AWSLoginHandler {
    /*
     * This handles the user authentication before they can use the app.
     */

    // AWS Logic
    AWSLoginModel awsLoginModel;


    // These declarations are now redundant.
    // UI References
    // Login Text Fields
    private EditText UsernameField, PasswordField;

    // User Registration Text Fields
    private EditText UsernameRegisterField, UserEmailRegisterField, PasswordRegisterField;

    // Confirm Registration.
    private EditText ConfirmationField;

    // Reset / Forgot User Fields.
    private EditText ResetCodeField, NewPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        // Prints this line to make sure that everything is working properly.
        println("Authentication Activity is loading...");

        // Instantiate AWSLoginModel.
        awsLoginModel = new AWSLoginModel(this, this);

        // Assign UI Variables into the project.
        UsernameField = findViewById(R.id.loginUser);
        PasswordField = findViewById(R.id.loginPassword);
        UsernameRegisterField = findViewById(R.id.registerUsername);
        UserEmailRegisterField = findViewById(R.id.registerEmail);
        PasswordRegisterField = findViewById(R.id.registerPassword);
        ConfirmationField = findViewById(R.id.confirmationCode);
        ResetCodeField = findViewById(R.id.resetCode);
        NewPasswordField = findViewById(R.id.newPassword);

        // Setting up listeners.
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.registerButton).setOnClickListener(this);
        findViewById(R.id.confirmButton).setOnClickListener(this);
        findViewById(R.id.resendConfirmationButton).setOnClickListener(this);
        findViewById(R.id.resetButton).setOnClickListener(this);
        findViewById(R.id.forgotButton).setOnClickListener(this);
        findViewById(R.id.showLoginButton).setOnClickListener(this);
        findViewById(R.id.showRegisterButton).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
            registerAction();
            break;

            case R.id.confirmButton:
            confirmAction();
            break;

            case R.id.loginButton:
                loginAction();
                break;

            case R.id.resendConfirmationButton:
                confirmAction();
                break;

            case R.id.resetButton:
                resetAction();
                break;

            case R.id.forgotButton:
                forgotPasswordAction();
                break;

            case R.id.showLoginButton:
                showLoginAction(true);
                break;

            case R.id.showRegisterButton:
                showRegisterAction(true);
                break;
        }
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
    public void onFailure(int process, Exception exception, int causeLimitExceeded, String s) {
        Toast.makeText(AuthenticationActivity.this,  s, Toast.LENGTH_LONG).show();
        if (causeLimitExceeded != AWSLoginModel.CAUSE_MUST_CONFIRM_FIRST) {
            exception.printStackTrace();
        } else {
            showConfirm(true);
        }
    }

    @Override
    public void onResendConfirmationCodeSuccess(String destination) {
        Toast.makeText(AuthenticationActivity.this, "Confirmation code sent! Destination:" + destination, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestResetUserPasswordSuccess(String destination) {
        Toast.makeText(AuthenticationActivity.this, "Reset code sent! Destination:" + destination, Toast.LENGTH_SHORT).show();
        showForgotAction(true);
    }

    @Override
    public void onResetUserPasswordSuccess() {
        Toast.makeText(AuthenticationActivity.this, "Password reset! Sign In!", Toast.LENGTH_LONG).show();
    }

    private void loginAction() {
        // Handles the user authentication.
        // Authenticate the user and handle UI flow through the app.
        awsLoginModel.signInUser(UsernameField.getText().toString(), PasswordField.getText().toString());
    }

    private void registerAction() {
        // Handle user registration.
        awsLoginModel.registerUser(UsernameRegisterField.getText().toString(),
                UserEmailRegisterField.getText().toString(), PasswordRegisterField.getText().toString());
    }

    private void confirmAction() {
        // Handle the verification to confirm that the user is not a bot.
        awsLoginModel.confirmRegistration(ConfirmationField.getText().toString());
    }

    private void resendConfirmationAction() {
        // Resend the conformation code.
        awsLoginModel.resendConfirmationCode();
    }

    public void forgotPasswordAction() {
        if (UsernameField.getText().toString().isEmpty()) {
            Toast.makeText(AuthenticationActivity.this, "Username is required.", Toast.LENGTH_LONG).show();
        } else {
            awsLoginModel.requestResetUserPassword(UsernameField.getText().toString());
        }
    }

    public void resetAction() {
        // This line needs to be fixed.
       // awsLoginModel.resetUserPasswordWithCode(ResetCodeField.getText().toString());
    }

    public void showLoginAction(boolean show) {
        if (show) {
            showRegisterAction(false);
            showConfirm(false);
            showForgotAction(false);
            findViewById(R.id.loginContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.showRegisterButton).setVisibility(View.VISIBLE);
            findViewById(R.id.showLoginButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.loginContainer).setVisibility(View.GONE);
            UsernameField.setText("");
            PasswordField.setText("");
        }
    }

    public void showRegisterAction(boolean show) {
        if (show) {
            showLoginAction(false);
            showConfirm(false);
            showForgotAction(false);
            findViewById(R.id.registerContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.showRegisterButton).setVisibility(View.VISIBLE);
            findViewById(R.id.showLoginButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.registerContainer).setVisibility(View.GONE);
            UsernameRegisterField.setText("");
            UserEmailRegisterField.setText("");
            PasswordRegisterField.setText("");
        }
    }

    public void showConfirm(boolean show) {
        if (show) {
            showLoginAction(false);
            showRegisterAction(false);
            showForgotAction(false);
        } else {
            findViewById(R.id.confirmContainer).setVisibility(View.VISIBLE);
            ConfirmationField.setText("");
        }
    }

    private void showForgotAction(boolean show) {
        if (show) {
            showLoginAction(false);
            showRegisterAction(false);
            showConfirm(false);
            findViewById(R.id.registerContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.showRegisterButton).setVisibility(View.GONE);
            findViewById(R.id.showLoginButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.forgotContainer).setVisibility(View.GONE);
            ResetCodeField.setText("");
            NewPasswordField.setText("");
        }
    }
}
