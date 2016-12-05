package com.example.daniel.conconnection;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckInFragment extends android.support.v4.app.Fragment {
    private static CheckInFragment instance;

    private GPSManager gpsManager;
    private ArrayList<Double> locationData;

    private User user;
    private FileManager fileManager;
    private Context context;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private ImageView userPicture;
    private Bitmap userPictureBitmap = null;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;

    /** Create a file Uri for saving an image or video */
    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }



    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            throw new RuntimeException("External Storage State: " + Environment.getExternalStorageState());
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ConConnection");
        File[] files = mediaStorageDir.listFiles();
        if(files !=null)
            for(File file: files){
                Log.d("mediaStorageFiles", file.getName());
            }
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            checkWritePermissions();
            //Attempt to make mediaStorageDir if it doesn't exist
            if (! mediaStorageDir.mkdirs()){
                //crash if unable
                Log.d("ConConnection", "failed to create directory");
                throw new RuntimeException("failed to create directory: " + mediaStorageDir.getPath());
                //return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private void checkWritePermissions(){
        //Check WRITE_EXTERNAL_STORAGE permission
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    throw new RuntimeException("WRITE_EXTERNAL_STORAGE permission not granted.");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public static CheckInFragment getInstance(){
        if(instance == null)
            instance = new CheckInFragment();
        return instance;
    }
    public CheckInFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //super.onActivityCreated(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_check_in, container, false);
        context = getContext();
        fileManager = new FileManager(context);
        user = fileManager.readUserFromFile();

        gpsManager = new GPSManager(context);
        locationData = gpsManager.getLocation();    //returns an ArrayList<Double> of (longitude, latitude)

        userPicture = (ImageView) rootView.findViewById(R.id.userPicture);
        if(userPictureBitmap != null){//Restore bitmap if it was previously set before being inflated
            userPicture.setImageBitmap(userPictureBitmap);
        }

        Button takePictureButton = (Button) rootView.findViewById(R.id.takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 11);



                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                try {
                    userPictureBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
                    userPicture.setImageBitmap(userPictureBitmap);
                    uploadPictureToFirebase(fileUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void uploadPictureToFirebase(Uri photoUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference myStorageRef = storage.getReferenceFromUrl("gs://conconnection-33940.appspot.com");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        File photoFile = new File(photoUri.getPath());

        StorageReference photoRef = myStorageRef.child("userPhotos").child(firebaseUser.getUid()).child(photoFile.getName());
        photoRef.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Get a URL to the uploaded content
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("uploadPictureToFirebase", downloadUrl.toString());
                //Delete old photo
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle unsuccesssful uploads
                Log.d("uploadPictureToFirebase", e.getMessage());
            }
        });
    }
}
