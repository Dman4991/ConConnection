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

import java.io.Serializable;
import java.util.ArrayList;

public class AccountManagementFragment extends Fragment implements Serializable {
    private static AccountManagementFragment instance;
    private View rootView;

    private User user;
    private FileManager fileManager;
    private Context context;

    public static AccountManagementFragment getInstance(){
        if(instance == null)
            instance = new AccountManagementFragment();
        return instance;
    }
    public AccountManagementFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account_management, container, false);
        Context context = getContext();
        FileManager fileManager = new FileManager(context);

        User user = fileManager.readUserFromFile();
        Log.d("File", "From AccountManagement User="+user.toString());

        setAccountManagementDoneButton(rootView);

        return rootView;
    }

    void setAccountManagementDoneButton(View rootView){
        Button doneButton = (Button) rootView.findViewById(R.id.account_done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getContext();
                fileManager = new FileManager(context);
                user = fileManager.readUserFromFile();
                Log.d("File", "From Account Management user="+user.toString());
                user.setAutoLogin(true);

                ArrayList<String> fields = getFields();
                String name = fields.get(0);
                String age = fields.get(1);
                String bio = fields.get(2);
                String bodytype = fields.get(3);
                if(name.length()<=0 || age.length()<=0 || bio.length()<=0 || bodytype.length()<=0){
                    Log.d("File", "At least one field was empty, do nothing, return\n"+"name="+name+" age="+age+" bio="+bio+" bodytype="+bodytype);
                    //prompt user to enter in fields
                    //do nothing, return
                    return;
                }
                else{
                    Log.d("File", "Adding to user data\n"+"name="+name+" age="+age+" bio="+bio+" bodytype="+bodytype);
                    //fill file data
                    user.setName(name);
                    user.setAge(Short.parseShort(age));
                    user.setBiography(bio);
                    user.setBodyType(bodytype);

                    fileManager.writeUserToFile(user);
                }

            }
        });
    }

    public ArrayList<String> getFields(){
        ArrayList<String> fields = new ArrayList();
        //check if all text fields are valid
        //if so then add to the user file
        //should have already checked for local file of user info on login
        fields.add(((AutoCompleteTextView) rootView.findViewById(R.id.name)).getText().toString());
        fields.add(((EditText) rootView.findViewById(R.id.age)).getText().toString());
        fields.add(((EditText) rootView.findViewById(R.id.bio)).getText().toString());
        fields.add(((EditText) rootView.findViewById(R.id.bodytype)).getText().toString());
        return fields;
    }
}
