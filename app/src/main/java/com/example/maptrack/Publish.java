package com.example.maptrack;



import androidx.appcompat.app.AppCompatActivity;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedHashMap;

public class Publish extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient; // Object used to receive location updates
    TextView tv;
    private LocationRequest locationRequest; // Object that defines important parameters regarding location request.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        tv = (TextView) findViewById(R.id.textView);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000); // 5 second delay between each request
        locationRequest.setFastestInterval(5000); // 5 seconds fastest time in between each request
        locationRequest.setSmallestDisplacement(10); // 10 meters minimum displacement for new location request
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // enables GPS high accuracy location requests

        sendUpdatedLocationMessage();

    }
    private void sendUpdatedLocationMessage() {
        try {
            mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    tv.setText("location published");
                    Location location = locationResult.getLastLocation();
                    LinkedHashMap<String, String> message = getNewLocationMessage(location.getLatitude(), location.getLongitude());
                    Backendless.Messaging.publish("driver1", message, new AsyncCallback<MessageStatus>() {
                        @Override
                        public void handleResponse(MessageStatus response) {
                            Log.i( "MYAPP", "message has  been published" );
                            Toast.makeText(getApplicationContext(),"Published successfully",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e( "MYAPP", "server reported an error " + fault );
                            Toast.makeText(getApplicationContext(),"Biscuit",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            },Looper.myLooper());
        }
        catch (SecurityException e) {
            e.printStackTrace();
                }
    }


    private LinkedHashMap<String, String> getNewLocationMessage(double lat, double lng) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("lat", String.valueOf(lat));
        map.put("lng", String.valueOf(lng));
        return map;
    }
}
