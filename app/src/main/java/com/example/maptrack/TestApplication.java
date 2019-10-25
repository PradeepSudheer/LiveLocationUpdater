package com.example.maptrack;

import android.app.Application;

import com.backendless.Backendless;

public class TestApplication extends Application {
    public static final String APPLICATION_ID  = "FBB3E2B8-0D11-09CF-FFD2-23758F7BEB00";
    public static final String API_KEY = "FCFA0100-F819-1CC8-FF29-0F2B82266B00";
    public static final String SERVER_URL = "https://api.backendless.com";

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp( getApplicationContext(),APPLICATION_ID,API_KEY);
    }
}
