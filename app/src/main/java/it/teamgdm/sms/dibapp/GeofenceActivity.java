package it.teamgdm.sms.dibapp;

import android.app.AlertDialog;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

public abstract class GeofenceActivity extends BaseActivity implements GeofenceBroadcastReceiver.GeofenceBroadcastReceiverCallback {

    private GeofenceBroadcastReceiver geofenceBroadcastReceiver;
    private GeofenceAPI geofenceAPI;
    Bundle savedInstanceState;
    static int geofenceReceiverLastAction = 0;
    IntentFilter intentFilter;
    Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        geofenceBroadcastReceiver = new GeofenceBroadcastReceiver(this);
        geofenceAPI = new GeofenceAPI(this);
        intentFilter = new IntentFilter(Constants.GEOFENCE_TRANSITION_ACTION);
        arguments  = new Bundle();
        registerReceiver(geofenceBroadcastReceiver, intentFilter);
        geofenceHandler();
    }

    @Override
    protected void onStart() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onPause-");
        super.onDestroy();
        geofenceAPI.removeGeofences();
        unregisterReceiver(geofenceBroadcastReceiver);
    }

    private void geofenceHandler(){
        Log.i(Constants.TAG, getClass().getSimpleName() + " -geofenceHandler-");
        if(geofenceAPI.hasGeofencePermissions()) {
            geofenceAPI.geofenceInit();
        } else {
            geofenceAPI.askGeofencePermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onRequestPermissionsResult-");
        // If request is cancelled, the result arrays are empty.
        if(requestCode == Constants.GEOFENCE_PERMISSION_REQUEST_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onRequestPermissionsResult-PERMISSION GRANTED");
                // permission was granted, yay!
                Session.geofencePermissionGranted = true;
            } else {
                Log.i(Constants.TAG, getClass().getSimpleName() + " -onRequestPermissionsResult-PERMISSION DENIED");
                Session.geofencePermissionGranted = false;
                // permission denied, boo!
                new AlertDialog.Builder(this)
                        .setTitle(R.string.geofence_insufficient_permissions_title)
                        .setMessage(R.string.geofence_insufficient_permissions_message)
                        .create()
                        .show();
            }
        }
    }


}
