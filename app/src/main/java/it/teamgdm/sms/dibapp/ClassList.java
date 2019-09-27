package it.teamgdm.sms.dibapp;

import android.util.Log;
import androidx.annotation.NonNull;

import org.json.JSONArray;

import java.util.ArrayList;

abstract class ClassList {

    static ArrayList<ClassLesson> classList;

    ClassList() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassList-Constructor-");
        classList = new ArrayList<>();
    }

    ArrayList<ClassLesson> getClassList() {
        Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -getClassList-");
        return classList;
    }

    abstract void setClassList(JSONArray classListData);

    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return classList.toString();
    }

    static ClassLesson getClassFromID(int classLessonID) {
        ClassLesson classLesson = null;
        for(int i=0; i<classList.size(); i++) {
            if(classList.get(i).getID() == classLessonID) {
                classLesson = classList.get(i);
            }
        }
        return classLesson;
    }
}
