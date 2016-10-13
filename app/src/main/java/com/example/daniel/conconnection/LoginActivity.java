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

import java.io.IOException;

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
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //local variables
    private boolean newUser = false;
    private User user = new UserProfile();
    private FileManager fileManager;
    private Context context;

    private Intent nextIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        context = getApplicationContext();
        fileManager = new FileManager(context);
        //following line is for testing new user data, deleteFile() is a private method, make public to test new user data
        //fileManager.deleteFile();

        //firebase preliminary code
//        Firebase.setAndroidContext(this);
//        myFirebaseRef = new Firebase("https://sizzling-torch-7552.firebaseio.com");
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(!isNewUser()){
            //currently there is no way for user to set autologin back to true once they hit the logout button which sets it to false
            //Following line is to force auto login if a user file exist, TODO delete following line once we understand how we want to logoff
            //user.setAutoLogin(true);
            if(user.getAutoLogin()){
                attemptLoginExistingUser();
            }
            //else do nothing, user manually logged off on last session
        }

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        assert mEmailSignInButton != null;
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                user.setEmail("myemail@example.com");
                user.setPassword("mypassword");
                fileManager.writeUserToFile(user);
                nextIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(nextIntent);
                */

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
                    login(email, password);
                    /*try {
                        Log.d("Login","New user login");
                        attemptLoginNewUser();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        });

        Button newAccountButton = (Button) findViewById(R.id.create_new_account_sign_in_button);
        assert newAccountButton != null;
        newAccountButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nextIntent = new Intent(getBaseContext(), NewAccountActivity.class);
                startActivity(nextIntent);
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
        //if file was found
        if(fileManager.doesFileExist()){
            //login with file
            Log.d("File", "User file was found");
            newUser = false;
            return false;
        }//if file was not found
        else{
            Log.d("File", "User file was not found");
            fileManager.writeUserToFile(user);
            return true;
        }
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
        /*
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
        */
        //comment out next line when file system works
        nextIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(nextIntent);

    }

    public void attemptLoginExistingUser(){
        //grab email and password data from file
        //check against firebase
        user = fileManager.readUserFromFile();
        String email = user.getEmail();
        String password = user.getPassword();

        login(email, password);
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



    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("signIn", "signInWithEmail:onComplete:" + task.isSuccessful());
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w("signIn", "signInWithEmail:failed", task.getException());
                    Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                } else {//Logged in
                    //Upload user object to firebase

                    nextIntent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(nextIntent);
                }
            }
        });
    }
}

