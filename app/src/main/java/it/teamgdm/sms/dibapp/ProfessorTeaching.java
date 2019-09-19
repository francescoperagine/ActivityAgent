package it.teamgdm.sms.dibapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ProfessorTeaching extends ClassList{

    ProfessorTeaching() {
    }

    @Override
    void setClassList(JSONArray professorTeachingData) {
        Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setClassList- "+professorTeachingData);
        for (int i=0; i< professorTeachingData.length(); i++) {
            Exam exam = new Exam();
            try {
                JSONObject o = professorTeachingData.getJSONObject(i);
                exam.setID(o.optInt(Constants.KEY_CLASS_ID));
                exam.setName(o.optString(Constants.KEY_CLASS_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            classList.add(exam);
        }
    }
}