package it.teamgdm.sms.dibapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.GeofencingEvent;

public class DibappBroadcastReceiver extends BroadcastReceiver {

    /** Geofence broadcast interface for callback **/

    interface GeofenceReceiverInterface {
        void onGeofenceTransitionAction(int geofenceReceiverAction);
    }

    private GeofenceReceiverInterface geofenceReceiverInterfaceCallback;

    DibappBroadcastReceiver(GeofenceReceiverInterface callback) {
        this.geofenceReceiverInterfaceCallback = callback;
    }

    static int geofenceLastTriggeredAction;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-");
        if(intent.getAction()!=null) {
            if (Constants.GEOFENCE_TRANSITION_ACTION.equals(intent.getAction())) {
                geofenceIntentReceiver(context, intent);
            }
        }
    }

    private void geofenceIntentReceiver(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Gets the transition event
        int geofenceTransitionAction = geofencingEvent.getGeofenceTransition();
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-geofenceTransitionAction " + geofenceTransitionAction);

        // Forwards the transition event only if there's a variation
        if(geofenceTransitionAction == geofenceLastTriggeredAction) return;
        geofenceLastTriggeredAction = geofenceTransitionAction;

        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(context, geofencingEvent.getErrorCode());
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-ERROR-" + errorMessage);
            return;
        }
        geofenceReceiverInterfaceCallback.onGeofenceTransitionAction(geofenceTransitionAction);
    }
}