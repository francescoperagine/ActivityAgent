package it.teamgdm.sms.dibapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;

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
                Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setClassList- OBJ " + o);
                classLesson.ID = o.optInt(Constants.KEY_CLASS_ID);
                classLesson.name = o.optString(Constants.KEY_CLASS_NAME);
                classLesson.year = o.optInt(Constants.KEY_CLASS_LESSON_YEAR);
                classLesson.semester = o.optInt(Constants.KEY_CLASS_LESSON_SEMESTER);
                classLesson.timeStart = classLesson.setTime(o.optString(Constants.KEY_CLASS_LESSON_TIME_START));
                classLesson.timeEnd = classLesson.setTime(o.optString(Constants.KEY_CLASS_LESSON_TIME_END));
        //        classLesson.passed = o.optBoolean(Constants.KEY_PASSED);
        //       classLesson.vote = o.optInt(Constants.KEY_VOTE);
        //        classLesson.praise = o.optBoolean(Constants.KEY_PRAISE);
        //        classLesson.setPassedDate(o.optString(Constants.KEY_PASSED_DATE));
            } catch (JSONException e) {
                Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setClassList- IS NULL");
            }
            classList.add(classLesson);
        }
    }
}
