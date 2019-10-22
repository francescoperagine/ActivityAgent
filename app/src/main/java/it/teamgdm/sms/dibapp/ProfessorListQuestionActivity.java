package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ProfessorListQuestionActivity extends BaseActivity {

    //create ArrayList of String
    final ArrayList<String> questionArray = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -onCreate-");
        super.onCreate(savedInstanceState);

        //create object of listview
        ListView listView= findViewById(R.id.listview);

        int lessonID = getIntent().getIntExtra(Constants.KEY_LESSON_ID, 0);
        String date = getIntent().getStringExtra(Constants.KEY_LESSON_DATE);

        getSupportActionBar().setTitle(getResources().getString(R.string.question_title) + " - " + date);

        //query
        JSONArray response = DAO.getLessonQuestion(lessonID);

        Log.i(Constants.TAG, "QUESTION RESPONSE = " + response.toString() + response.length());

        //Add elements to arraylist
        int totalQuestion = response.length();
        for (int i = 0; i < totalQuestion; i++) {
            try {
                String qst = response.getJSONObject(i).optString("question");
                questionArray.add(qst);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Create Adapter
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questionArray);

        //assign adapter to listview
        listView.setAdapter(arrayAdapter);

    }

    @Override
    int getLayoutResource() {
        return R.layout.professor_list_questions;
    }

}
