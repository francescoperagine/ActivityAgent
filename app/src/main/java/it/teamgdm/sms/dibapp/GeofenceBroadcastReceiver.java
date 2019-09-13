package it.teamgdm.sms.dibapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    Context context;

    @Override
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
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Intent geofenceTransitionIntent = new Intent(context, ExamDetailActivity.class);
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                Toast.makeText(context, context.getString(R.string.geofence_transition_entered), Toast.LENGTH_LONG).show();

                geofenceTransitionIntent.putExtra(Constants.GEOFENCE_TRANSITION_DWELLS,true);
            } else {
                geofenceTransitionIntent.putExtra(Constants.GEOFENCE_TRANSITION_DWELLS,false);
                Toast.makeText(context, context.getString(R.string.geofence_transition_exit), Toast.LENGTH_LONG).show();
            }
            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition, triggeringGeofences);
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-Transition details " + geofenceTransitionDetails);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-Transition details " + context.getString(R.string.geofence_transition_invalid_type));
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
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                return context.getString(R.string.geofence_transition_dwelling);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return context.getString(R.string.geofence_transition_exit);
            default:
                return context.getString(R.string.unknown_geofence_transition);
        }
    }
}
