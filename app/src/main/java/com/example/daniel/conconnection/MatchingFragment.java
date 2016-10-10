package com.example.daniel.conconnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MatchingFragment extends android.support.v4.app.Fragment {
    private static MatchingFragment instance;

    private User user = null;
    private Context context;
    private FileManager fileManager;

    private ImageView potentialMatchPicture;
    private Bitmap potentialMatchBitmap;

    public static MatchingFragment getInstance(){
        if(instance == null)
            instance = new MatchingFragment();
        return instance;
    }
    public MatchingFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_matching, container, false);
        context = getContext();
        fileManager = new FileManager(context);
        user = fileManager.readUserFromFile();
        Log.d("File", "In Matching Fragment, user=" + user.toString());

        Button yesButton = (Button) rootView.findViewById(R.id.yesButton);
        Button noButton = (Button) rootView.findViewById(R.id.noButton);
        potentialMatchPicture = (ImageView) rootView.findViewById(R.id.userPicture);
        if(potentialMatchBitmap != null){//Restore bitmap if it was previously set before being inflated
            potentialMatchPicture.setImageBitmap(potentialMatchBitmap);
        }

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mark as a match in database, get next potential match picture and info
                potentialMatchPicture.setImageResource(R.mipmap.ic_launcher);
                potentialMatchPicture.buildDrawingCache();//TODO check memory usage
                potentialMatchBitmap = Bitmap.createBitmap(potentialMatchPicture.getDrawingCache());
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mark as not a match in database, get next potential match picture and info
                potentialMatchPicture.setImageResource(R.drawable.housingicon);
                potentialMatchPicture.buildDrawingCache();//TODO check memory usage
                potentialMatchBitmap = Bitmap.createBitmap(potentialMatchPicture.getDrawingCache());
            }
        });

        return rootView;
    }
}
