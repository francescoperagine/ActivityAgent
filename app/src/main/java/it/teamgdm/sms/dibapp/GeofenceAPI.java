package it.teamgdm.sms.dibapp;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

class GeofenceAPI {

    private Context context;
    private ArrayList<Geofence> geofenceList;
    private PendingIntent geofencePendingIntent;
    private GeofencingClient geofencingClient;
    private String[] permissions;

    GeofenceAPI(Context context) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -constructor-");
        this.context = context;
        geofenceList = new ArrayList<>();
        permissions  = setGeofencePermissions();
    }

    void geofenceInit() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -geofenceInit-");
        geofencingClient = LocationServices.getGeofencingClient(context);
        setGeofences();
        registerGeofences();
    }

    private void setGeofences() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setGeofences-");
        Geofence dibGeofence = getGeofence(Constants.GEOFENCE_DIB_NAME, Constants.GEOFENCE_DIB_LATITUDE, Constants.GEOFENCE_DIB_LONGITUDE, Constants.GEOFENCE_METER_RADIUS_DIB, Constants.GEOFENCE_DIB_NAME);
        Geofence pdaGeofence = getGeofence(Constants.GEOFENCE_PDA_NAME, Constants.GEOFENCE_PDA_LATITUDE, Constants.GEOFENCE_PDA_LONGITUDE, Constants.GEOFENCE_METER_RADIUS_PDA, Constants.GEOFENCE_PDA_NAME);
        geofenceList.add(dibGeofence);
        geofenceList.add(pdaGeofence);
    }

    private Geofence getGeofence(String geofenceName, double latitude, double longitude, int circularMeterRadius, String requestID) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofence-");
        Geofence.Builder builder = new Geofence.Builder();
        builder.setRequestId(geofenceName);
        builder.setCircularRegion(latitude, longitude, circularMeterRadius);
        builder.setExpirationDuration(Geofence.NEVER_EXPIRE);
        builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        builder.setLoiteringDelay(Constants.GEOFENCE_TRANSITION_DWELL_TIME);
        builder.setRequestId(requestID);
        return builder.build();
    }

    private void registerGeofences() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -registerGeofences-");

        geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
            .addOnSuccessListener((Activity) context, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -registerGeofences-onSuccess");
                }
            })
            .addOnFailureListener((Activity) context, new OnFailureListener() {
            @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i(Constants.TAG, getClass().getSimpleName() + " -registerGeofences-onFailure " );
                    e.printStackTrace();
                    LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    assert manager != null;
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        Log.e("Provider", "Provider is not avaible");
                    }
                    if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                        Log.e("Network Provider", "Provider is not avaible");
                    }
                }
            });
    }

    private GeofencingRequest getGeofencingRequest() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofencingRequest-");
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL | GeofencingRequest.INITIAL_TRIGGER_EXIT);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofencePendingIntent-");
        int requestID = (int) System.currentTimeMillis();
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent == null) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofencePendingIntent-NULL");
            Intent geofenceBroadcastIntent = new Intent(Constants.GEOFENCE_TRANSITION_ACTION);
            // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
            // calling addGeofences() and removeGeofences().
            geofencePendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), requestID, geofenceBroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        if (geofencePendingIntent == null) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofencePendingIntent-STILL NULL!!!");
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofencePendingIntent-NOT NULL ANYMORE.");
        }
        return geofencePendingIntent;
    }

    void removeGeofences() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -removeGeofences-");
        if(Session.geofencePermissionGranted) {
            geofencingClient.removeGeofences(geofencePendingIntent);
            Log.i(Constants.TAG, getClass().getSimpleName() + " -removeGeofences-Geofences removed from pending intent");
        }
    }

    private String[] setGeofencePermissions() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setGeofencePermissions-");
        String[] permissions;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            permissions = new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            };
        } else {
            permissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
        }
        return permissions;
    }

    boolean hasGeofencePermissions() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -hasGeofencePermissions-");
        for(String permission : permissions) {
            if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        Session.geofencePermissionGranted = true;
        return true;
    }

    void askGeofencePermissions() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -askGeofencePermissions-");
        ActivityCompat.requestPermissions((Activity) context, permissions, Constants.GEOFENCE_PERMISSION_REQUEST_CODE);
    }

}
