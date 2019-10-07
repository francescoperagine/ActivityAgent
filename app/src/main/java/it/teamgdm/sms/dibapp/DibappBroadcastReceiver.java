package it.teamgdm.sms.dibapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.GeofencingEvent;

public class DibappBroadcastReceiver extends BroadcastReceiver {

    /** Geofence broadcast interface for callback **/

    static int geofenceLastTriggeredAction;

    public interface GeofenceBroadcastReceiverInterface {
        void onGeofenceTransitionAction(int geofenceReceiverAction);
    }

    private GeofenceBroadcastReceiverInterface geofenceBroadcastReceiverInterfaceCallback;

    DibappBroadcastReceiver(GeofenceBroadcastReceiverInterface callback) {
        this.geofenceBroadcastReceiverInterfaceCallback = callback;
    }

    /** Lesson broadcast interface for callback **/

    public interface LessonBroadcastReceiverInterface {
        void onNewQuestionSent();
    }

    private LessonBroadcastReceiverInterface lessonBroadcastReceiverInterfaceCallback;

    DibappBroadcastReceiver(LessonBroadcastReceiverInterface callback) {
        this.lessonBroadcastReceiverInterfaceCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onReceive-");
        if(intent.getAction()!=null) {
            switch (intent.getAction()) {
                case Constants.GEOFENCE_TRANSITION_ACTION:
                    geofenceIntentReceiver(context, intent);
                    break;
                case Constants.LESSON_NEW_QUESTION:
                    lessonBroadcastReceiverInterfaceCallback.onNewQuestionSent();
                    break;
                default: break;
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
        geofenceBroadcastReceiverInterfaceCallback.onGeofenceTransitionAction(geofenceTransitionAction);
    }




}