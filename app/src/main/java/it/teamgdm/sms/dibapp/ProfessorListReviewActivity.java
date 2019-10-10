package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

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
        listView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        int lessonID = intent.getExtras().getInt("LESSON");

        //query

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_LESSON_REVIEWS);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        JSONArray response = DAO.getFromDB(params);

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
