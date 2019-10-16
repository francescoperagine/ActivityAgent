package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ProfessorListReviewActivity extends BaseActivity {

    //a List of type hero for holding list items
    ArrayList<Review> reviewList;

    //the listview
    ListView listView;

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

        //creating the adapter
        ReviewListAdapter adapter = new ReviewListAdapter(this, R.layout.review_list, reviewList);

        //attaching adapter to the listview
        listView.setAdapter(adapter);
    }

    @Override
    int getLayoutResource() {
        return R.layout.professor_list_reviews;
    }

}
