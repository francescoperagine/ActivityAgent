package it.teamgdm.sms.dibapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ProfessorTeaching extends LessonList {

    ProfessorTeaching() {
    }

    @Override
    void setClassList(JSONArray professorTeachingClassData) {
        Log.i(Constants.TAG, ProfessorTeaching.class.getSimpleName() + " -setClassList- "+professorTeachingClassData);
        for (int i=0; i< professorTeachingClassData.length(); i++) {
            Lesson lesson = new Lesson();
            try {
                JSONObject o = professorTeachingClassData.getJSONObject(i);
                lesson.classID = o.optInt(Constants.KEY_CLASS_ID);
                lesson.className = o.optString(Constants.KEY_CLASS_NAME);
                lesson.classCode = o.optInt(Constants.KEY_CLASS_CODE);
                lesson.classDescription = o.optString(Constants.KEY_CLASS_DESCRIPTION);
                lesson.classYear = o.optInt(Constants.KEY_LESSON_YEAR);
                lesson.classsemester = o.optInt(Constants.KEY_LESSON_SEMESTER);
            } catch (JSONException e) {
                Log.i(Constants.TAG, ProfessorTeaching.class.getSimpleName() + " -setLessonList- IS NULL");
            }
            lessonList.add(lesson);
        }
    }

    @Override
    void setLessonList(JSONArray professorTeachingLessonData) {
        Log.i(Constants.TAG, ProfessorTeaching.class.getSimpleName() + " -setLessonList- "+professorTeachingLessonData);
        for (int i=0; i< professorTeachingLessonData.length(); i++) {
            Lesson lesson = new Lesson();
            try {
                JSONObject o = professorTeachingLessonData.getJSONObject(i);
                lesson.lessonID = o.optInt(Constants.KEY_LESSON_ID);
                lesson.classID = o.optInt(Constants.KEY_CLASS_ID);
                lesson.calendarID = o.optInt(Constants.KEY_LESSON_CALENDAR_ID);
                lesson.className = o.optString(Constants.KEY_CLASS_NAME);
                lesson.classCode = o.optInt(Constants.KEY_CLASS_CODE);
                lesson.classDescription = o.optString(Constants.KEY_CLASS_DESCRIPTION);
                lesson.classYear = o.optInt(Constants.KEY_LESSON_YEAR);
                lesson.classsemester = o.optInt(Constants.KEY_LESSON_SEMESTER);
                lesson.timeStart = lesson.getTimestampFromString(o.optString(Constants.KEY_LESSON_TIME_START));
                lesson.timeEnd = lesson.getTimestampFromString(o.optString(Constants.KEY_LESSON_TIME_END));
                lesson.lessonSummary = o.optString(Constants.KEY_LESSON_SUMMARY);
                lesson.lessonDescription = o.optString(Constants.KEY_LESSON_DESCRIPTION);
                lesson.rating = (float) o.optDouble(Constants.KEY_LESSON_REVIEW_RATING);
                lesson.attendance = o.optInt(Constants.KEY_LESSON_REVIEW_ATTENDANCE);
            } catch (JSONException e) {
                Log.i(Constants.TAG, ProfessorTeaching.class.getSimpleName() + " -setLessonList- IS NULL");
            }
            lessonList.add(lesson);
        }
    }
}
