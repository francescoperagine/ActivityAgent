package it.teamgdm.sms.dibapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class StudentCareer extends LessonList {

    StudentCareer() {
    }

    @Override
    void setClassList(JSONArray classListData) {

    }

    @Override
    void setLessonList(JSONArray studentCareerData) {
        Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setLessonList- "+studentCareerData);
        for (int i=0; i< studentCareerData.length(); i++) {
            Lesson lesson = new Lesson();
            try {
                JSONObject o = studentCareerData.getJSONObject(i);
                Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setLessonList- OBJ " + o);
                lesson.classID = o.optInt(Constants.KEY_CLASS_ID);
                lesson.lessonID = o.optInt(Constants.KEY_LESSON_ID);
                lesson.name = o.optString(Constants.KEY_CLASS_NAME);
                lesson.classDescription = o.optString(Constants.KEY_CLASS_DESCRIPTION);
                lesson.code = o.optInt(Constants.KEY_CLASS_CODE);
                lesson.year = o.optInt(Constants.KEY_LESSON_YEAR);
                lesson.semester = o.optInt(Constants.KEY_LESSON_SEMESTER);
                lesson.timeStart = lesson.getTimestampFromString(o.optString(Constants.KEY_LESSON_TIME_START));
                lesson.timeEnd = lesson.getTimestampFromString(o.optString(Constants.KEY_LESSON_TIME_END));
                lesson.lessonSummary = o.optString(Constants.KEY_LESSON_SUMMARY);
                lesson.lessonDescription = o.optString(Constants.KEY_LESSON_DESCRIPTION);
        //        lesson.passed = o.optBoolean(Constants.KEY_PASSED);
        //       lesson.vote = o.optInt(Constants.KEY_VOTE);
        //        lesson.praise = o.optBoolean(Constants.KEY_PRAISE);
        //        lesson.setPassedDate(o.optString(Constants.KEY_PASSED_DATE));
            } catch (JSONException e) {
                Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setLessonList- IS NULL");
            }
            lessonList.add(lesson);
        }
    }
}
