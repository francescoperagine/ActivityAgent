package it.teamgdm.sms.dibapp;

import android.app.AlertDialog;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Objects;

public abstract class GeofenceFragment extends BaseFragment {

    private GeofenceBroadcastReceiver geofenceBroadcastReceiver;
    private GeofenceAPI geofenceAPI;
    static int geofenceReceiverLastAction = 0;

    public void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);
        // Sets the BroadcastReceiver to receive only the transition's updates from the GeofenceAPI pending intent
    }

    public void onStart() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();
        geofenceHandler();
    }

    private void geofenceHandler(){
        Log.i(Constants.TAG, getClass().getSimpleName() + " -geofenceHandler-");
        geofenceAPI = new GeofenceAPI(getContext());
        if(geofenceAPI.checkGeofencePermissions()) {
            geofenceBroadcastReceiver = new GeofenceBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter(Constants.GEOFENCE_TRANSITION_ACTION);
            Objects.requireNonNull(getContext()).registerReceiver(geofenceBroadcastReceiver, intentFilter);

            geofenceAPI.geofenceInit();
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
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.geofence_insufficient_permissions_title)
                        .setMessage(R.string.geofence_insufficient_permissions_message)
                        .create()
                        .show();
            }
        }
    }

    @Override
    public void onPause() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onPause-");
        super.onPause();
        geofenceAPI.removeGeofences();
        getContext().unregisterReceiver(geofenceBroadcastReceiver);
    }

}
