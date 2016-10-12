package com.example.daniel.conconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class NewAccountActivity extends AppCompatActivity {

    Button mDoneButton;
    EditText mEmail, mPassword, mConfirmPassword, mGender,
             mAge, mBodyType, mBiography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

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

                //if any are null, empty, or not valid
                    //bring user focus to error
                //else
                    //write to firebase
                    //write to file
                    //go to main activity
            }
        });


    }
}
