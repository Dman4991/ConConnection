package com.example.daniel.conconnection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class AccountManagementFragment extends Fragment {

    public static AccountManagementFragment newInstance(){
        AccountManagementFragment fragment = new AccountManagementFragment();
        return fragment;
    }
    public AccountManagementFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_management, container, false);

        //should check for local file of user info on login
        View mNameView = (AutoCompleteTextView) rootView.findViewById(R.id.name);
        View mAgeView = (EditText) rootView.findViewById(R.id.age);
        View mBioView = (EditText) rootView.findViewById(R.id.bio);
        View mBodytypeView = (EditText) rootView.findViewById(R.id.bodytype);

        return rootView;
    }
}
