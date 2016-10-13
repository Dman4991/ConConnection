package com.example.daniel.conconnection;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class NewAccountActivity extends AppCompatActivity {

    Button mDoneButton;
    EditText mEmail, mPassword, mConfirmPassword, mGender,
             mAge, mBodyType, mBiography;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Intent nextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Sign in", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Sign in", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mDoneButton = (Button) findViewById(R.id.done_button_new_account);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDoneButton = (Button) findViewById(R.id.done_button_new_account);
                mEmail = (EditText) findViewById(R.id.email_new_account);
                mPassword = (EditText) findViewById(R.id.password_new_account);
                mConfirmPassword = (EditText) findViewById(R.id.confirm_password_new_account);
                mGender = (EditText) findViewById(R.id.gender_new_account);
                mAge = (EditText) findViewById(R.id.age_new_account);
                mBodyType = (EditText) findViewById(R.id.bodytype_new_account);
                mBiography = (EditText) findViewById(R.id.bio_new_account);



                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(email.length()==0 || password.length()==0){
                    //if any are null, empty, or not valid
                    //bring user focus to error
                    Toast.makeText(NewAccountActivity.this, "enter your email & password", Toast.LENGTH_SHORT).show();
                } else{
                    //else
                    //write to firebase
                    //write to file
                    //go to main activity
                    createAccount(email, password);
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("create account", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(NewAccountActivity.this, "account creation failed",
                                    Toast.LENGTH_SHORT).show();
                        } else {//Logged in
                            //Upload user object to firebase
                            User newUser = new UserProfile(email, password, "", (short) 0, "", "", new ArrayList<Integer>());
                            //Write user to file
                            nextIntent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(nextIntent);
                        }

                        // ...
                    }
                });
    }
}
