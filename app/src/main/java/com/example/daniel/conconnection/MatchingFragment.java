package com.example.daniel.conconnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

    private ImageView potentialMatchPicture;

    public static MatchingFragment newInstance(){
        MatchingFragment fragment = new MatchingFragment();
        return fragment;
    }
    public MatchingFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_matching, container, false);


        Button yesButton = (Button) rootView.findViewById(R.id.yesButton);
        Button noButton = (Button) rootView.findViewById(R.id.noButton);
        potentialMatchPicture = (ImageView) rootView.findViewById(R.id.userPicture);

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

        return rootView;
    }
}
