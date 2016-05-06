package com.example.daniel.conconnection;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class AccountManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        View mNameView = (AutoCompleteTextView) findViewById(R.id.name);
        View mAgeView = (EditText) findViewById(R.id.age);
        View mBioView = (EditText) findViewById(R.id.bio);
        View mBodytypeView = (EditText) findViewById(R.id.bodytype);


    }
}
