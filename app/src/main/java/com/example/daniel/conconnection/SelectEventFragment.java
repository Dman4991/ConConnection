package com.example.daniel.conconnection;

import android.Manifest;
import android.app.Activity;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

public class SelectEventFragment extends android.support.v4.app.Fragment {

    private static SelectEventFragment instance;

    private GPSManager gpsManager;
    private ArrayList<Double> locationData;

    //file manager will be used to add events to the user object
    private User user;
    private FileManager fileManager;

    private Context context;


    public static SelectEventFragment getInstance() {
        if (instance == null)
            instance = new SelectEventFragment();
        return instance;
    }

    public SelectEventFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("GPS","in onCreateView");
        //super.onActivityCreated(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_select_event, container, false);
        context = getContext();
        //    fileManager = new FileManager(context);
        //    user = fileManager.readUserFromFile();

        gpsManager = new GPSManager(context);
        //locationData = gpsManager.getLocation();    //returns an ArrayList<Double> of (longitude, latitude)

        setUniversityEventButton(rootView);
        setDTLAEventButton(rootView);

        return rootView;
    }

    private void setUniversityEventButton(final View rootView) {
        Button universityEventButton = (Button) rootView.findViewById(R.id.university_event_button);
        universityEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GPS", "University Event Clicked");
                double longitudeEvent = 34.057382;
                double latitudeEvent = -117.822887;
                locationData = gpsManager.getLocation();
                Log.d("GPS", "Select Event About to request location update");
                Log.d("GPS", "Select Event After request location update long="+locationData.get(0)+" lat="+locationData.get(1));
                if(inRange(longitudeEvent, latitudeEvent)){
                    Toast.makeText(context, "Successful check in", Toast.LENGTH_SHORT).show();
                    //change to CheckInFragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, CheckInFragment.getInstance())
                            .commit();
                }
                else{
                    Toast.makeText(context, "Too far from event", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDTLAEventButton(View rootView) {
        Button dtlaButton = (Button) rootView.findViewById(R.id.dtla_event_button);
        dtlaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GPS", "DTLA Event Clicked");
                double longitudeEvent = 34.040858;
                double latitudeEvent = -118.268601;
                locationData = gpsManager.getLocation();
                if(inRange(longitudeEvent, latitudeEvent)){
                    Toast.makeText(context, "Successful check in", Toast.LENGTH_SHORT).show();
                    //change to CheckInFragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, CheckInFragment.getInstance())
                            .commit();
                }
                else{
                    Toast.makeText(context, "Too far from event", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Common way to measure distance from point to point considering the earths curvature.
     * Needs further testing to confirm it works properly.
     */
    private boolean inRange(double eventLat, double eventLong){
        Log.d("GPS", "in inRange()");
        double R = 3961;    //radius of Earth in miles
        //see if you are in range of 1 mile
        double myLong = locationData.get(0);
        double myLat = locationData.get(1);

        double diffLong = degToRadian(myLong - eventLong);
        double diffLat = degToRadian(myLat - eventLat);

        double a = Math.pow(Math.sin(diffLat/2.0), 2) + Math.cos(eventLat) * Math.cos(myLat) * Math.pow(Math.sin(diffLong/2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;

        Log.d("GPS", "Distance="+distance);
        double maxMilesFromEvent = 35.0;  //miles from event
        if(distance<=maxMilesFromEvent){
            return true;
        }
        return false;
    }

    private double degToRadian(double degree){
        return degree * (Math.PI/180.0);
    }
}
