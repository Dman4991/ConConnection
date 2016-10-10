package com.example.daniel.conconnection;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Daniel on 10/9/2016.
 */

public class FileManager {
    Context context = null;
    String fileName = "UserFile.txt";

    public FileManager() {

    }

    public FileManager(Context context){
        this.context = context;
    }

    //reads user object from file and returns it
    //returns null if it fails
    public User readUserFromFile(){
        User user = null;
        try{
            Log.d("File", "Attempting to read file from File Manager");
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(fileName));
            user = (User) inputStream.readObject();
            inputStream.close();
            Log.d("File", "Read User from file: "+user.toString());

            //add to user

            //save user back to file, may have to delete and create a new file
        }catch(Exception e){
            Log.e("File", "Failed to read user from file in account management", e);
        }

        return user;
    }

    //returns true if user was successfully written to file, returns false otherwise
    public boolean writeUserToFile(User user){
        deleteFile();
        createFile();

        try {
            Log.d("File", "Trying to write to file");
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            //following 2 lines are for testing
            outputStream.writeObject(user);
            outputStream.close();
            Log.d("File", "Wrote user to file");
            return true;
        }catch (Exception e1){
            Log.e("File", "Filed to write user object to file", e1);
            return false;
        }
    }

    //creates a user file
    public boolean createFile(){
        File file = new File(context.getFilesDir().getAbsolutePath(), fileName);
        try{
            file.createNewFile();
            Log.d("File", "Created new file");
            return true;
        }catch (Exception f){
            Log.e("File", "Failed to create new file", f);
            return false;
        }
    }

    private boolean deleteFile(){
        File file = new File(context.getFilesDir().getAbsolutePath(), fileName);
        try {
            file.delete();
            Log.d("File", "Deleted file");
            return true;
        }
        catch(Exception e){
            Log.e("File", "Failed to delete file", e);
            return false;
        }
    }

    public boolean doesFileExist(){
        FileInputStream localUserFile = null;
        try{
            localUserFile = context.openFileInput(fileName);
                    return true;
        }
        catch(FileNotFoundException e){
            return false;
        }
    }
}
