package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * An activity representing a single Exam detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ExamListActivity}.
 */
public class ExamDetailActivity extends BaseActivity implements BaseFragment.OnClickedItemListener{

    Exam exam;

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

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
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            int id = getIntent().getIntExtra(ExamDashboardFragment.ARG_ITEM_ID, 0);
            Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate- ID " + id);
            arguments.putInt(ExamDashboardFragment.ARG_ITEM_ID, id);
            ExamDashboardFragment fragment = new ExamDashboardFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.examDetailContainer, fragment).commit();
        }
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onAttachFragment-");
        if(fragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            baseFragment.setOnClickedItemListener(this);
        }
    }

    @Override
    int getLayoutResource() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.activity_exam_detail;
    }

    @Override
    String setActivityTitle() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setActivityTitle- ");
        int id = getIntent().getIntExtra(ExamDashboardFragment.ARG_ITEM_ID, 0);
        exam = StudentCareer.getExam(id);
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setActivityTitle- exam " + exam);
        return exam.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ExamListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(int examID, String examName, String selectedAction) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onItemSelected-id " + examID + " name " + examName + " " + selectedAction);
    }

}
