package it.teamgdm.sms.dibapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfessorListQuestionActivity extends BaseActivity {

    //create ArrayList of String
    final ArrayList<String> questionArray = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //create object of listview
        ListView listView=(ListView)findViewById(R.id.listview);

        Intent intent = getIntent();
        int lessonID = intent.getExtras().getInt("LESSON");

        //query

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.KEY_ACTION, Constants.GET_LESSON_QUESTIONS);
        params.put(Constants.KEY_CLASS_LESSON_ID, String.valueOf(lessonID));
        JSONArray response = DAO.getFromDB(params);

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
