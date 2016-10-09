package com.example.daniel.conconnection;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    /*
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    //for accessing firebase
    private Firebase myFirebaseRef = null;
    private String userExtension = "/users/";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //local variables
    private boolean newUser = false;
    private User user = new UserProfile();

    private Intent nextIntent = null;
    private String userFile = "userFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        //firebase preliminary code
//        Firebase.setAndroidContext(this);
//        myFirebaseRef = new Firebase("https://sizzling-torch-7552.firebaseio.com");
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        if(isNewUser()) {
//            //set next activity to be AccountManagement
//            nextIntent = new Intent(getBaseContext(), AccountManagementFragment.class);
//        }
//        else{
//            //attempt login via file
//            attemptLoginExistingUser();
//        }

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        assert mEmailSignInButton != null;
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("signIn", "clicked");
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                Log.d("signIn", "email: " + email + email.length());
                Log.d("signIn", "password: " + password + password.length());
                //Input validation
                if(email.length()==0 && password.length()==0){
                    Toast.makeText(LoginActivity.this, "enter your email & password", Toast.LENGTH_SHORT).show();
                } else if(email.length()==0){
                    Toast.makeText(LoginActivity.this, "enter your email", Toast.LENGTH_SHORT).show();
                } else if(password.length()==0){
                    Toast.makeText(LoginActivity.this, "enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    //Valid input, attempt login

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("signIn", "signInWithEmail:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("signIn", "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "do", Toast.LENGTH_SHORT).show();
                            } else {//Logged in
                                nextIntent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(nextIntent);
                            }
                        }


                    });
                    /*try {
                        Log.d("Login","New user login");
                        attemptLoginNewUser();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("mAuthListener", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("mAuthListener", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public boolean isNewUser(){
        //check if user file exists to auto login
        FileInputStream localUserFile = null;
        try {
            localUserFile = openFileInput(userFile);
        } catch (FileNotFoundException e) {
            Log.d("File", "User file was not found");

            Context context = getApplicationContext();

            //create file
            File file = new File(context.getFilesDir().getAbsolutePath(), userFile);
            try{
                file.createNewFile();
                Log.d("File", "Created new file");
            }catch (Exception f){
                Log.e("File", "Failed to create new file", f);
            }

            try {
                Log.d("File", "Trying to write to file");
                ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(userFile, Context.MODE_PRIVATE));
                //following 2 lines are for testing
                user.setEmail("useremail@test.com");
                user.setPassword("userPassword");
                outputStream.writeObject(user);
                outputStream.close();
                Log.d("File", "Wrote user to file");
            }catch (Exception e1){
                Log.e("File", "Filed to write user object to file", e1);
            }

            newUser = true;
            return true;
        }

        //if file was found
        if(localUserFile!=null){
            //login with file
            Log.d("File", "User file was found");
            newUser = false;
            return false;
        }
        return false;
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLoginNewUser() throws IOException {
        // if (mAuthTask != null) {
        //     return;
        // }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        View focusView = null;
        boolean cancel = false;

        //check if email or password are empty or not valid
        if(email.equals("") || isEmailValid(email)){
            focusView = mEmailView;
            mEmailView.setText("");
            cancel = true;
        }
        if(password.equals("") || !isPasswordValid(password)){
            focusView = mPasswordView;
            mPasswordView.setText("");
            cancel = true;
        }
        if(cancel) {
            focusView.requestFocus();
        }

        //if neither email or password field are empty and both are valid
        //attempt login through firebase
        //create user on firebase

        //move user to AccountManagement
        user.setEmail(email);
        user.setPassword(password);
        nextIntent.putExtra("UserData", (Serializable) user);
        //comment out next line when file system works
        nextIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(nextIntent);

    }

    public void attemptLoginExistingUser(){
        //grab email and password data from file
        //check against firebase
        //if valid then send user to main activity
        nextIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(nextIntent);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}

