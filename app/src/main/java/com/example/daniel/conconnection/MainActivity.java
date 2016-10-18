package com.example.daniel.conconnection;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private FirebaseStorage storage;
//    private StorageReference myStorageRef;

    private User user;
    private FileManager fileManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("onCreate", "mainActivity onCreate");
//        storage = FirebaseStorage.getInstance();

        context = getApplicationContext();
        fileManager = new FileManager(context);
        user = fileManager.readUserFromFile();
        Log.d("File", "From MainActivity user="+user.toString());
        //for checking logout functionality

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Log.d("onCreate", "storageRef");
//        myStorageRef = storage.getReferenceFromUrl("gs://conconnection-33940.appspot.com");
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), "ConConnection");
//        File[] files = mediaStorageDir.listFiles();
//        if(files!=null) {
//            Uri fileUri = Uri.fromFile(files[0]);
//            Log.d("onCreate", files[0].getAbsolutePath());
//            StorageReference imageRef = myStorageRef.child(files[0].getName());
//            imageRef.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    //Get a URL to the uploaded content
//                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                    Log.d("onCreate", downloadUrl.toString());
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    //Handle unsuccesssful uploads
//                    Log.d("onCreate", e.getMessage());
//                }
//            });
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Display MatchingFragment by default
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, MatchingFragment.getInstance())
                    .commit();
            getSupportActionBar().setTitle("Matches");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_matches) {
//            fragmentManager.beginTransaction()
//                    .remove(fragmentManager.findFragmentById(R.id.content_frame))
//                    .commit();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, MatchingFragment.getInstance())
                    .commit();
            getSupportActionBar().setTitle("Matches");
        } else if (id == R.id.nav_check_in) {
//            fragmentManager.beginTransaction()
//                    .remove(fragmentManager.findFragmentById(R.id.content_frame))
//                    .commit();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, CheckInFragment.getInstance())
                    .commit();
            getSupportActionBar().setTitle("Check In");
        } else if (id == R.id.nav_accout_management) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, AccountManagementFragment.getInstance())
                    .commit();
            getSupportActionBar().setTitle("Account Management");
        }else if(id==R.id.nav_logout){
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, LogoutFragment.getInstance())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
