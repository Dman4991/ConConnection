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
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountManagementFragment extends Fragment implements Serializable {
    private static AccountManagementFragment instance;
    private String userFile = "userFile.txt";
    private User thisUsersFile;
    private View rootView;

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

                //attempt to read from file
                User user = null;
                Context context = getContext();

                File file = new File(context.getFilesDir().getAbsolutePath(), userFile);
                //file.delete();
                //Log.d("File", "Deleted file");
                try{
                    Log.d("File", "Attempting to read file from account management");
                    ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(userFile));
                    user = (User) inputStream.readObject();
                    inputStream.close();
                    Log.d("File", "Read User from file"+user.toString());

                    //add to user

                    //save user back to file, may have to delete and create a new file
                }catch(Exception e){
                    Log.e("File", "Failed to read user from file in account management", e);
                }

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


                    //File file = new File(context.getFilesDir().getAbsolutePath(), userFile);
                    file.delete();
                    Log.d("File", "Deleted file");

                    //write to file
                    //create file
                    try{
                        file.createNewFile();
                        Log.d("File", "Created new file");
                    }catch (Exception f){
                        Log.e("File", "Failed to create new file", f);
                    }

                    try {
                        Log.d("File", "Trying to write to file");
                        ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(userFile, Context.MODE_PRIVATE));
                        outputStream.writeObject(user);
                        outputStream.close();
                        Log.d("File", "Wrote user to file");
                    }catch (Exception e1){
                        Log.e("File", "Filed to write user object to file", e1);
                    }
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
