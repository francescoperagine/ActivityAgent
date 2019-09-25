package it.teamgdm.sms.dibapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class StudentCareer extends ClassList{

    StudentCareer() {
    }

    @Override
    void setClassList(JSONArray studentCareerData) {
        Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setClassList- "+studentCareerData);
        for (int i=0; i< studentCareerData.length(); i++) {
            ClassLesson classLesson = new ClassLesson();
            try {
                JSONObject o = studentCareerData.getJSONObject(i);
                classLesson.setID(o.optInt(Constants.KEY_CLASS_ID));
                classLesson.setName(o.optString(Constants.KEY_CLASS_NAME));
                classLesson.setYear(o.optInt(Constants.KEY_CLASS_LESSON_YEAR));
                classLesson.setSemester(o.optInt(Constants.KEY_CLASS_LESSON_SEMESTER));
                classLesson.setPassed(o.optBoolean(Constants.KEY_PASSED));
                classLesson.setVote(o.optInt(Constants.KEY_VOTE));
                classLesson.setPraise(o.optBoolean(Constants.KEY_PRAISE));
                classLesson.setPassedDate(o.optString(Constants.KEY_PASSED_DATE));
            } catch (JSONException e) {
                Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setClassList- IS NULL");
            }
            classList.add(classLesson);
        }
    }
}
