package it.teamgdm.sms.dibapp;

import android.util.Log;
import androidx.annotation.NonNull;

import org.json.JSONArray;

import java.util.ArrayList;

abstract class LessonList {

    static ArrayList<Lesson> lessonList;

    LessonList() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -constructor-");
        lessonList = new ArrayList<>();
    }

    ArrayList<Lesson> getLessonList() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getLessonList-");
        return lessonList;
    }

    abstract void setClassList(JSONArray classListData);
    abstract void setLessonList(JSONArray classListData);

    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return lessonList.toString();
    }

    static Lesson getLessonFromID(int lessonID) {
        Log.i(Constants.TAG, LessonList.class.getSimpleName() + " -getLessonFromID-");
        Lesson lesson = null;
        for(int i = 0; i< lessonList.size(); i++) {
            if(lessonList.get(i).lessonID == lessonID) {
                lesson = lessonList.get(i);
            }
        }
        return lesson;
    }
}
