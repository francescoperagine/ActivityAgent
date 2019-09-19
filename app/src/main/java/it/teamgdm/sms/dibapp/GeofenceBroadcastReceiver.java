package it.teamgdm.sms.dibapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    static int geofenceLastTriggeredAction = 0;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-");
        this.context = context;

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

        Intent geofenceTransitionIntent = new Intent(context, ClassDetailActivity.class);
        geofenceTransitionIntent.setAction(Constants.GEOFENCE_RECEIVER_ACTION);
        geofenceTransitionIntent.putExtra(Constants.GEOFENCE_RECEIVER_ACTION, geofenceTransition);
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-GEOFENCE_TRANSITION_ACTION-RECEIVED code " + geofenceTransition);
        context.startActivity(geofenceTransitionIntent);
    }
}