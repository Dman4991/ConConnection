package com.example.daniel.conconnection;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Daniel on 10/26/2016.
 */

public class GPSManager {

    private LocationListener locationListener;
    private LocationManager locationManager;
    private Context context;
    private double longitude;
    private double latitude;

    public GPSManager(Context context) {
        Log.d("GPS","GPSManager Creating GPSManager");
        this.context = context;
        locationManager = (LocationManager) this.context.getSystemService(context.LOCATION_SERVICE);
        setLocationListener();
    }

    private void setLocationListener() {
        Log.d("GPS","GPSManager in setLocationListener()");
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    public ArrayList<Double> getLocation() throws SecurityException{
        Log.d("GPS", "GPSManager In getLocation()");
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
       // Log.d("GPS", "Before GPS request");
        //Log.d("GPS", "Latitude: "+latitude+" Longitude: "+longitude);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        //Log.d("GPS", "Latitude: "+latitude+" Longitude: "+longitude);
        ArrayList<Double> returnArray = new ArrayList<Double>();
        returnArray.add(longitude);
        returnArray.add(latitude);
        return returnArray;
    }
}
