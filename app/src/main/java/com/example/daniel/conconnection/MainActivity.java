package com.example.daniel.conconnection;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private ImageView potentialMatchPicture;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }
    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            throw new RuntimeException("External Storage State: " + Environment.getExternalStorageState());
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        /*
//            Code for GPS location initialization/call
//         */
//
//        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                /*
//                    To view GPS data, run in debug mode then go to android monitor, should continuously update location data.
//                        TODO: Make it
//                            - A a few GPS calls when check-in event is running.
//                 */
//                String myLocation = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
//                Log.d("GPS", myLocation);
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//        //should explicitly check if permision is available
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Log.i("GPS",  "Before GPS request");
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        Log.i("GPS", "After GPS request");


        Button yesButton = (Button) findViewById(R.id.yesButton);
        Button noButton = (Button) findViewById(R.id.noButton);
        potentialMatchPicture = (ImageView) findViewById(R.id.potentialMatchPicture);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mark as a match in database, get next potential match picture and info
                potentialMatchPicture.setImageResource(R.mipmap.ic_launcher);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mark as not a match in database, get next potential match picture and info
                potentialMatchPicture.setImageResource(R.drawable.housingicon);
            }
        });


        Button takePictureButton = (Button) findViewById(R.id.takePictureButton);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                    potentialMatchPicture.setImageBitmap(photo);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
