package it.teamgdm.sms.dibapp;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentCareer {
    private static ArrayList<Exam> examList;

    StudentCareer(JSONArray studentCareerData) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -StudentCareer-Constructor-");
        examList = new ArrayList<>();
        setExamList(studentCareerData);
    }

    public static ArrayList<Exam> getExamList() {
        Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -getExamList-");
        return examList;
    }

    public static void setExamList(JSONArray studentCareerData) {
        Log.i(Constants.TAG, StudentCareer.class.getSimpleName() + " -setExamList- "+studentCareerData);
        for (int i=0; i< studentCareerData.length(); i++) {
            Exam exam = new Exam();
            try {
                JSONObject o = studentCareerData.getJSONObject(i);
                exam.setID(o.optInt(Constants.KEY_CLASS_ID));
                exam.setName(o.optString(Constants.KEY_CLASS_NAME));
                exam.setYear(o.optInt(Constants.KEY_CLASS_YEAR));
                exam.setSemester(o.optInt(Constants.KEY_CLASS_SEMESTER));
                exam.setPassed(o.optBoolean(Constants.KEY_PASSED));
                exam.setVote(o.optInt(Constants.KEY_VOTE));
                exam.setPraise(o.optBoolean(Constants.KEY_PRAISE));
                exam.setPassedDate(o.optString(Constants.KEY_PASSED_DATE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            examList.add(exam);
        }
    }

    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return examList.toString();
    }

    static Exam getExam(int examID) {
        Exam e = null;
        for(int i=0; i<examList.size(); i++) {
            if(examList.get(i).getID() == examID) {
                e = examList.get(i);
            }
        }
        return e;
    }
}
