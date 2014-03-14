package com.example.GPS;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.*;

public class MyActivity extends Activity {

    Button btnShowLocation;

    // GPSTracker class
    GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnShowLocation = (Button) findViewById(R.id.button);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MyActivity.this);

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    File file = new File("c:/newfile.txt");
                    String content = Double.toString(latitude)+","+Double.toString(longitude);

                    try (FileOutputStream fop = new FileOutputStream(file)) {

                        // if file doesn't exists, then create it
                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        // get the content in bytes
                        byte[] contentInBytes = content.getBytes();

                        fop.write(contentInBytes);
                        fop.flush();
                        fop.close();

                        System.out.println("Done");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
    }

}



