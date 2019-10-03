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
                Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setClassList- OBJ " + o);
                classLesson.classID = o.optInt(Constants.KEY_CLASS_ID);
                classLesson.lessonID = o.optInt(Constants.KEY_CLASS_LESSON_ID);
                classLesson.name = o.optString(Constants.KEY_CLASS_NAME);
                classLesson.classDescription = o.optString(Constants.KEY_CLASS_DESCRIPTION);
                classLesson.code = o.optInt(Constants.KEY_CLASS_CODE);
                classLesson.year = o.optInt(Constants.KEY_CLASS_LESSON_YEAR);
                classLesson.semester = o.optInt(Constants.KEY_CLASS_LESSON_SEMESTER);
                classLesson.date = classLesson.getDate(o.optString(Constants.KEY_CLASS_LESSON_DATE));
                classLesson.timeStart = classLesson.getTime(o.optString(Constants.KEY_CLASS_LESSON_TIME_START));
                classLesson.timeEnd = classLesson.getTime(o.optString(Constants.KEY_CLASS_LESSON_TIME_END));
                classLesson.lessonSummary = o.optString(Constants.KEY_CLASS_LESSON_SUMMARY);
                classLesson.lessonDescription = o.optString(Constants.KEY_CLASS_LESSON_DESCRIPTION);
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
