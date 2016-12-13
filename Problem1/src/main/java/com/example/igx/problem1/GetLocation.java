package com.example.igx.problem1;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

/**
 * Created by kiseong on 2016-12-13.
 */
public class GetLocation extends Service implements LocationListener {


    private final Context kcontext;
    boolean GPSEnabled = false;
    boolean NetworkEnabled = false;
    boolean GetLocation = false;
    Location location;
    double Lat;
    double Lon;


    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 20;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;

    public GetLocation(Context context) {
        this.kcontext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) kcontext.getSystemService(LOCATION_SERVICE);
            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            NetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!GPSEnabled && !NetworkEnabled) {
            } else {
                this.GetLocation = true;
                if (NetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {

                            Lat = location.getLatitude();
                            Lon = location.getLongitude();
                        }
                    }
                }

                if (GPSEnabled) {
                    if (location == null) {locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                Lat = location.getLatitude();
                                Lon = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }


public void stopUsingGPS(){
    if(locationManager != null){
        locationManager.removeUpdates(GetLocation.this);
    }
}


    public double getLatitude(){
        if(location != null){
            Lat = location.getLatitude();
        }
        return Lat;
    }


    public double getLongitude(){
        if(location != null){
            Lon = location.getLongitude();
        }
        return Lon;
    }

    public boolean isGetLocation() {
        return this.GetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(kcontext);

        alertDialog.setTitle("GPS 사용 세팅");
        alertDialog.setMessage("GPS 셋팅이 안되있습니다..\n 설정창으로 이동할까요?");


        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                       kcontext.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onLocationChanged(Location location) {

        // TODO Auto-generated method stub

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

}

