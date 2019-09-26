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
            ClassLesson classLesson = new ClassLesson();
            try {
                JSONObject o = professorTeachingData.getJSONObject(i);
                classLesson.ID = o.optInt(Constants.KEY_CLASS_ID);
                classLesson.name = o.optString(Constants.KEY_CLASS_NAME);
            } catch (JSONException e) {
                Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setClassList- IS NULL");
            }
            classList.add(classLesson);
        }
    }
}
