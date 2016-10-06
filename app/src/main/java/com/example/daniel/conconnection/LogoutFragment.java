package com.example.daniel.conconnection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Daniel on 10/5/2016.
 */

public class LogoutFragment extends android.support.v4.app.Fragment {
    private static LogoutFragment instance;

    public static LogoutFragment getInstance(){
        if(instance == null)
            instance = new LogoutFragment();
        return instance;
    }
    public LogoutFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_logout, container, false);

        Button logoutButton = (Button) rootView.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sets user.autologin to false
                //exits the app or goes to login screen
            }
        });


        return rootView;
    }

}
