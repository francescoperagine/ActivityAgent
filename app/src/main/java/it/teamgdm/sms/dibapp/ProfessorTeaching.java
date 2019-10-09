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
        Log.i(Constants.TAG, ProfessorTeaching.class.getSimpleName() + " -setClassList- "+professorTeachingData);
        for (int i=0; i< professorTeachingData.length(); i++) {
            ClassLesson classLesson = new ClassLesson();
            try {
                JSONObject o = professorTeachingData.getJSONObject(i);
                classLesson.ID = o.optInt(Constants.KEY_CLASS_ID);
                classLesson.name = o.optString(Constants.KEY_CLASS_NAME);
                classLesson.classDescription = o.optString(Constants.KEY_CLASS_DESCRIPTION);
                classLesson.code = o.optInt(Constants.KEY_CLASS_CODE);
                classLesson.year = o.optInt(Constants.KEY_CLASS_LESSON_YEAR);
                classLesson.semester = o.optInt(Constants.KEY_CLASS_LESSON_SEMESTER);
            } catch (JSONException e) {
                Log.i(Constants.TAG, ProfessorTeaching.class.getSimpleName() + " -setClassList- IS NULL");
            }
            classList.add(classLesson);
        }
    }
}
