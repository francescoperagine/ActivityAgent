package it.teamgdm.sms.dibapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ProfessorTeaching extends LessonList {

    ProfessorTeaching() {
    }

    @Override
    void setLessonList(JSONArray professorTeachingData) {
        Log.i(Constants.TAG, ProfessorTeaching.class.getSimpleName() + " -setLessonList- "+professorTeachingData);
        for (int i=0; i< professorTeachingData.length(); i++) {
            Lesson lesson = new Lesson();
            try {
                JSONObject o = professorTeachingData.getJSONObject(i);
                lesson.ID = o.optInt(Constants.KEY_CLASS_ID);
                lesson.name = o.optString(Constants.KEY_CLASS_NAME);
                lesson.classDescription = o.optString(Constants.KEY_CLASS_DESCRIPTION);
                lesson.code = o.optInt(Constants.KEY_CLASS_CODE);
                lesson.year = o.optInt(Constants.KEY_CLASS_LESSON_YEAR);
                lesson.semester = o.optInt(Constants.KEY_CLASS_LESSON_SEMESTER);
            } catch (JSONException e) {
                Log.i(Constants.TAG, ProfessorTeaching.class.getSimpleName() + " -setLessonList- IS NULL");
            }
            lessonList.add(lesson);
        }
    }
}
