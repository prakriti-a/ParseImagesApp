package com.prakriti.parseimagesapp;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Yxkr26lUahdkJLgLOiayS2cBnkgYaO1cRyYBswGS")
                .clientKey("j5iwGgeG38sHfz0fEd46B9HOxtXCApii1NRdzFhX")
                .server("https://parseapi.back4app.com/")
                .build());
    }
}
