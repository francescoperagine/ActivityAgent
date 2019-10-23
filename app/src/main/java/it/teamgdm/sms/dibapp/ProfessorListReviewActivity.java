package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ProfessorListReviewActivity extends BaseActivity {

    //a List of type hero for holding list items
    ArrayList<Review> reviewList;

    //the listview
    ListView listView;

    ReviewListAdapter adapterMax;
    ReviewListAdapter adapterMin;

    Boolean filterFlag = false;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing objects
        reviewList = new ArrayList<>();
        listView = findViewById(R.id.listView);

        int lessonID = getIntent().getIntExtra(Constants.KEY_LESSON_ID, 0);
        String date = getIntent().getStringExtra(Constants.KEY_LESSON_DATE);

        getSupportActionBar().setTitle(getResources().getString(R.string.review_title) + " - " + date);

        //query
        JSONArray response = DAO.getLessonReview(lessonID);

        Log.i(Constants.TAG, "REVIEWS RESPONSE = " + response.toString() + response.length());

        //Add elements to arraylist
        int totalQuestion = response.length();
        for (int i = 0; i < totalQuestion; i++) {
            try {
                float rate = (float) response.getJSONObject(i).optDouble("rating");
                String summary = response.getJSONObject(i).optString("summary");
                String description = response.getJSONObject(i).optString("review");
                Review tmp = new Review(rate, summary, description);
                reviewList.add(tmp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //creating sorted lists
        reviewList.sort(new ComparatorReviewMaxToMin());
        ArrayList<Review> reviewListMax = (ArrayList<Review>) reviewList.clone();
        reviewList.sort(new ComparatorReviewMaxToMin().reversed());
        ArrayList<Review> reviewListMin = (ArrayList<Review>) reviewList.clone();

        //creating the adapters
        adapterMax = new ReviewListAdapter(this, R.layout.review_list, reviewListMax);
        adapterMin = new ReviewListAdapter(this, R.layout.review_list, reviewListMin);

        //attaching adapter to the listview
        listView.setAdapter(adapterMax);

    }

    @Override
    int getLayoutResource() {
        return R.layout.professor_list_reviews;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreateOptionsMenu-");
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onOptionsItemSelected-");
        int id = item.getItemId();
        switch (id) {
            case R.id.filterButton:

                String toastSortText;

                if(filterFlag == false){
                    listView.setAdapter(adapterMin);
                    item.setIcon(R.drawable.ic_ascend_star);
                    filterFlag = true;
                    toastSortText = getString(R.string.sort_ascend);
                }
                else{
                    listView.setAdapter(adapterMax);
                    item.setIcon(R.drawable.ic_descend_star);
                    filterFlag = false;
                    toastSortText = getString(R.string.sort_descend);
                }

                //if the filter button is pressed again before the previous toast is dismissed, this control dismiss it immediately
                if (toast != null) {
                    toast.cancel();
                }

                toast = Toast.makeText(getApplicationContext(), toastSortText, Toast.LENGTH_SHORT);
                toast.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
