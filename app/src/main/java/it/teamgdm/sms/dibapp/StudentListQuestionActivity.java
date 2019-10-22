package it.teamgdm.sms.dibapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class StudentListQuestionActivity extends BaseActivity {

    //create ArrayList of String
    private ArrayList<Question> questionArray = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //create object of listview
        ListView listView= findViewById(R.id.listviewstudent);

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
                Question quest = new Question();
                String qst = response.getJSONObject(i).optString("question");
                quest.setQuestion(qst);
                int id = response.getJSONObject(i).optInt("ID", 0);
                quest.setId(id);
                int rate = response.getJSONObject(i).optInt("rate", 0 );
                quest.setRate(rate);

                questionArray.add(quest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        StudentQuestionAdapter studentQuestionAdapter = new StudentQuestionAdapter(this, R.layout.question_item, questionArray);
        //Create Adapter
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questionArray);



        //assign adapter to listview
        listView.setAdapter(studentQuestionAdapter);

    }

    @Override
    int getLayoutResource() {
        return R.layout.activity_student_list_question;
    }

}
