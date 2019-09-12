package it.teamgdm.sms.dibapp;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class GeofenceAPI {

    private Context context;
    private ArrayList<Geofence> geofenceList;
    private PendingIntent geofencePendingIntent;
    private GeofencingClient geofencingClient;

    GeofenceAPI(Context context) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -constructor-");
        this.context = context;
        geofenceList = new ArrayList<>();
    }

    public void onCreate() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        geofencingClient = LocationServices.getGeofencingClient(context);
    }

    void geofenceInit() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -geofenceInit-");
        if(checkGeofencePermissions()) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -geofenceInit-we got permissions");
            setupGeofence();
            addGeofenceToGeofencingClient();
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -geofenceInit-we ain't got permissions");
            showSnackbar(context.getResources().getString(R.string.geofence_insufficient_permissions_title));
        }
    }

    private boolean checkGeofencePermissions() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -checkGeofencePermissions-");
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    private void showSnackbar(final String text) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -showSnackbar-");
        View container = ((Activity) context).findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void setupGeofence() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setupGeofence-");
        Geofence dibGeofence = getGeofence(Constants.GEOFENCE_DIB_NAME, Constants.GEOFENCE_DIB_LATITUDE, Constants.GEOFENCE_DIB_LONGITUDE);
        Geofence pdaGeofence = getGeofence(Constants.GEOFENCE_PDA_NAME, Constants.GEOFENCE_PDA_LATITUDE, Constants.GEOFENCE_PDA_LONGITUDE);
        geofenceList.add(dibGeofence);
        geofenceList.add(pdaGeofence);
    }

    private Geofence getGeofence(String geofenceName, double latitude, double longitude) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofence-");
        Geofence.Builder builder = new Geofence.Builder();

        builder.setRequestId(geofenceName);
        builder.setCircularRegion(latitude, longitude, Constants.GEOFENCE_METER_RADIUS);
        builder.setExpirationDuration(Geofence.NEVER_EXPIRE);
        builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL);

        return builder.build();
    }

    private void addGeofenceToGeofencingClient() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -addGeofenceToGeofencingClient-");
        geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());
    }

    private GeofencingRequest getGeofencingRequest() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofencingRequest-");
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofencePendingIntent-");
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent geofenceBroadcastReceiverIntent = new Intent(context, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(context, 0, geofenceBroadcastReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    void removeGeofences() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -removeGeofences-");
        geofencingClient.removeGeofences(geofencePendingIntent);
    }

}
