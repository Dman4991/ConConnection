package com.example.daniel.conconnection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AccountManagementFragment extends Fragment implements Serializable {
    private static AccountManagementFragment instance;
    private String userFile = "userFile.txt";
    private User thisUsersFile;

    public static AccountManagementFragment getInstance(){
        if(instance == null)
            instance = new AccountManagementFragment();
        return instance;
    }
    public AccountManagementFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_management, container, false);

        UserProfile newUser = null;
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null){
            newUser = (UserProfile) extras.getSerializable("myClass");
        }

        setAccountManagementDoneButton(rootView);

        return rootView;
    }

    void setAccountManagementDoneButton(View rootView){
        Button doneButton = (Button) rootView.findViewById(R.id.account_done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all text fields are valid
                //if so then add to the user file
                /*

                //should have already checked for local file of user info on login
                EditText mNameView = (AutoCompleteTextView) rootView.findViewById(R.id.name);
                EditText mAgeView = (EditText) rootView.findViewById(R.id.age);
                EditText mBioView = (EditText) rootView.findViewById(R.id.bio);
                EditText mBodytypeView = (EditText) rootView.findViewById(R.id.bodytype);

                newUser.setName(mNameView.getText().toString());
                newUser.setAge(Short.parseShort(mAgeView.getText().toString()));
                newUser.setBiography(mBioView.getText().toString());
                newUser.setBodyType(mBodytypeView.getText().toString());
                */
                //attempt to read from file
                User user = null;
                Context context = getContext();
                try{
                    Log.d("File", "Attempting to read file from account management");
                    ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(userFile));
                    user = (User) inputStream.readObject();
                    inputStream.close();
                    Log.d("File", "Read User from file email=" + user.getEmail() + " password="+user.getPassword());

                    //add to user

                    //save user back to file, may have to delete and create a new file
                }catch(Exception e){
                    Log.e("File", "Failed to read user from file in account management", e);
                }

                File file = new File(context.getFilesDir().getAbsolutePath(), userFile);
                //file.delete();
                //Log.d("File", "Deleted file");
            }
        });
    }
}
