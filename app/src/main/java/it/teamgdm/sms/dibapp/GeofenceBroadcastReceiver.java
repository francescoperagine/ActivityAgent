package it.teamgdm.sms.dibapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    static int geofenceLastTriggeredAction = 0;
    private GeofenceBroadcastReceiverInterface geofenceBroadcastReceiverInterfaceCallback;

    GeofenceBroadcastReceiver(GeofenceBroadcastReceiverInterface callback) {
        this.geofenceBroadcastReceiverInterfaceCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-");

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Gets the transition event
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Forwards the transition event only if there's a variation
        if(geofenceTransition == geofenceLastTriggeredAction) return;
        geofenceLastTriggeredAction = geofenceTransition;

        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(context, geofencingEvent.getErrorCode());
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-ERROR-" + errorMessage);
            return;
        }

        geofenceBroadcastReceiverInterfaceCallback.onGeofenceTransitionAction(geofenceTransition);
    }

    public interface GeofenceBroadcastReceiverInterface {
        void onGeofenceTransitionAction(int geofenceReceiverAction);
    }
}