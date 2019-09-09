package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

/**
 * An activity representing a single Exam detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ExamListActivity}.
 */
public class ExamDetailActivity extends BaseActivity {

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
            int id = getIntent().getIntExtra(ExamDetailFragment.ARG_ITEM_ID, 0);
            Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreate- ID " + id);
            arguments.putInt(ExamDetailFragment.ARG_ITEM_ID, id);
            ExamDetailFragment fragment = new ExamDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.examDetailContainer, fragment).commit();
        }
    }

    @Override
    int getLayoutResource() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getLayoutResource-");
        return R.layout.activity_exam_detail;
    }

    @Override
    String getActivityTitle() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getActivityTitle- ");
        int id = getIntent().getIntExtra(ExamDetailFragment.ARG_ITEM_ID, 0);
        Exam e = StudentCareer.getExam(id);
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getActivityTitle- exam " + e);
        return e.toString();
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
}
