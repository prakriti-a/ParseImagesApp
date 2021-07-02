package com.prakriti.parseimagesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // SIGN UP ACTIVITY

    private EditText edtSignUpEmail, edtSignUpUsername, edtSignUpPassword;
    private String email, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);

        findViewById(R.id.btnGoToLogin).setOnClickListener(this);
        findViewById(R.id.btnSignUp).setOnClickListener(this);

        // also create a listener for enter button on device keyboard
        edtSignUpPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // checking if user has pressed key
                    onClick(findViewById(R.id.btnSignUp)); // button is a view
                }
                return false;
            }
        });
    }

    // to disappear keyboard when user taps on screen -> set listener for root view of associated layout file
    public void hideKeyboard(View view) {
        // if keyboard is visible, only then hide it. Or else, crash
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            view.clearFocus();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoToLogin:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.btnSignUp:
                signUpNewUser();
                break;
        }
    }

    private void signUpNewUser() {
        if (AllAccessCodes.isFieldNull(edtSignUpEmail) || AllAccessCodes.isFieldNull(edtSignUpUsername) ||
                AllAccessCodes.isFieldNull(edtSignUpPassword)) {
            return;
        } else {
            // put parse codes in try-catch block for input errors
            try {
                email = edtSignUpEmail.getText().toString().trim();
                username = edtSignUpUsername.getText().toString().trim();
                password = edtSignUpPassword.getText().toString().trim();
                // also write code for checking repetition of usernames in db

                // User class that already exists in Parse Server
                ParseUser appUser = new ParseUser();
                appUser.setEmail(email);
                appUser.setUsername(username);
                appUser.setPassword(password);

                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Signing up...");
                progressDialog.show();

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "Sign Up Is Successful!", Toast.LENGTH_SHORT).show();
                            edtSignUpEmail.setText("");
                            edtSignUpUsername.setText("");
                            edtSignUpPassword.setText("");
                            startActivity(new Intent(MainActivity.this, ImageList.class));
                            // once logged in, user shouldn't log out by pressing back button
                            finish(); // finishing the Sign up activity so user cannot transition back to it
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Unable to sign up\n" + e.getMessage() + "\nPlease try again",
                                    Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

}