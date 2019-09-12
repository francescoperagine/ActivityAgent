package it.teamgdm.sms.dibapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Settings.System.getString;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    Context context;

    public void onReceive(Context context, Intent intent) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-");
        this.context = context;

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(context, geofencingEvent.getErrorCode());
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-ERROR-" + errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition, triggeringGeofences);
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-Transition details " + geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(TAG, context.getString(R.string.geofence_transition_invalid_type));
        }
    }

    private String getGeofenceTransitionDetails( int geofenceTransition, List<Geofence> triggeringGeofences) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getGeofenceTransitionDetails-");

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }

    private String getTransitionString(int transitionType) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getTransitionString-");
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return context.getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return context.getString(R.string.geofence_transition_exited);
            default:
                return context.getString(R.string.unknown_geofence_transition);
        }
    }

}
