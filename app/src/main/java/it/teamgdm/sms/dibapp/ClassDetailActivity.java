package it.teamgdm.sms.dibapp;

import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.Objects;

/**
 * An activity representing a single Exam detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ClassListActivity}.
 */
public class ClassDetailActivity extends GeofenceActivity implements BaseFragment.OnClickedItemListener{

    protected void onStart() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");
        super.onStart();

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //

        if (savedInstanceState == null) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onStart-");
            // Create the detail fragment and add it to the activity using a fragment transaction.
            if(getIntent().getAction() != null) {
                intentHandler();
                startFragment();
            }
        }
    }

    void intentHandler() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -intentHandler-");
        if(Objects.equals(getIntent().getAction(), Constants.CLASS_LIST_ACTION) & getIntent().hasExtra(Constants.KEY_ITEM_ID)) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -intentHandler-CLASS_LIST_ACTION"+ getIntent().getAction());
            int examID = getIntent().getIntExtra(Constants.KEY_ITEM_ID, 0);
            arguments.putInt(Constants.KEY_ITEM_ID, examID);
        }
    }

    @Override
    public void onTransitionAction(int geofenceTransitionAction) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onTransitionAction-"+ geofenceTransitionAction);
        if(geofenceTransitionAction != GeofenceActivity.geofenceReceiverLastAction) {
            // Forwards the new geofence's transition code to the fragment
            arguments.putInt(Constants.GEOFENCE_TRANSITION_ACTION, geofenceTransitionAction);
            GeofenceActivity.geofenceReceiverLastAction = geofenceTransitionAction;
            Log.i(Constants.TAG, getClass().getSimpleName() + " -onTransitionAction-ARGS"+ arguments);
            startFragment();
        }
    }

    void startFragment() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -startFragment-");
        ClassDashboardFragment fragment = new ClassDashboardFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.classDetailContainer, fragment).commit();
    }

    int getLayoutResource() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.activity_class_detail;
    }

    public void onItemSelected(int selectedActionResource) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onItemSelected-action " + selectedActionResource);

        Fragment fragment = null;
        switch (selectedActionResource) {
            case R.id.partecipate:
                setClassAttendance();
                fragment = new ClassDashboardFragment();
                break;
            case R.id.evaluate:
                //          fragment = new ClassEvaluateFragment();
                break;
            case R.id.history:
                //          fragment = new ClassHistoryFragment();
                break;
            default:
                break;
        }
        assert fragment != null;
        fragment.setArguments(arguments);
    }

    // Updates the class attendance flag
    private void setClassAttendance() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setClassAttendance-");
        if(Session.getSharedPreference(Constants.KEY_CLASS_PARTECIPATION, false)) {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setClassAttendance-FALSE");
            arguments.putBoolean(Constants.KEY_CLASS_PARTECIPATION, false);
        } else {
            Log.i(Constants.TAG, getClass().getSimpleName() + " -setClassAttendance-TRUE");
            arguments.putBoolean(Constants.KEY_CLASS_PARTECIPATION, true);
            // Register the lesson's attendance into the DB
        }
    }
}