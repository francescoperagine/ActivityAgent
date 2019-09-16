package it.teamgdm.sms.dibapp;

import android.util.Log;
import androidx.annotation.NonNull;

import org.json.JSONArray;

import java.util.ArrayList;

class ClassList {

    static ArrayList<Exam> classList;

    ClassList() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -ClassList-Constructor-");
        classList = new ArrayList<>();
    }

    ArrayList<Exam> getClassList() {
        Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -getClassList-");
        return classList;
    }

    void setClassList(JSONArray studentCareerData) {

    }

    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return classList.toString();
    }

    static Exam getClassFromID(int examID) {
        Exam e = null;
        for(int i=0; i<classList.size(); i++) {
            if(classList.get(i).getID() == examID) {
                e = classList.get(i);
            }
        }
        return e;
    }
}
