package com.example.daniel.conconnection;

import android.content.Intent;
import android.os.UserManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NewAccountActivity extends AppCompatActivity {

    Button mDoneButton;
    EditText mEmail, mPassword, mConfirmPassword, mName, mGender,
             mAge, mBodyType, mBiography;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Intent nextIntent;

    private FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        fileManager = new FileManager(getApplicationContext());

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
                mName = (EditText) findViewById(R.id.name_new_account);
                mGender = (EditText) findViewById(R.id.gender_new_account);
                mAge = (EditText) findViewById(R.id.age_new_account);
                mBodyType = (EditText) findViewById(R.id.bodytype_new_account);
                mBiography = (EditText) findViewById(R.id.bio_new_account);

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String fullName = mName.getText().toString();
                String gender = mGender.getText().toString();
                String stringAge = mAge.getText().toString();
                String bodyType = mBodyType.getText().toString();
                String biography = mBiography.getText().toString();

                if(email.length()==0 || password.length()==0 || confirmPassword.length()==0 || fullName.length()==0 || gender.length()==0 || stringAge.length()==0 || bodyType.length()==0 || biography.length()==0){
                    //if any are null, empty, or not valid
                    //bring user focus to error
                    Toast.makeText(NewAccountActivity.this, "please enter all fields", Toast.LENGTH_SHORT).show();
                } else if(!email.matches("\\w+@[a-zA-Z]+\\.[a-zA-Z]{2,3}")){
                    Toast.makeText(NewAccountActivity.this, "invalid email", Toast.LENGTH_SHORT).show();
                } else if(!password.equals(confirmPassword)) {
                    Toast.makeText(NewAccountActivity.this, "passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    //write to firebase
                    //write to file
                    //go to main activity
                    createAccount(email, password, fullName, gender, Integer.parseInt(stringAge), bodyType, biography);
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

    private void createAccount(final String email, final String password, final String name, final String gender, final int age, final String bodyType, final String biography){
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
                            User newUser = new UserProfile(email, password, name, age, bodyType, biography, new ArrayList<Integer>());
                            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("userIds").push().setValue(uId);
                            mDatabase.child("users").child(uId).setValue(newUser);

                            //Write user to file
                            fileManager.writeUserToFile(newUser);

                            Toast.makeText(NewAccountActivity.this, "account creation successful", Toast.LENGTH_SHORT).show();
                            nextIntent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(nextIntent);
                        }
                    }
                });
    }
}
