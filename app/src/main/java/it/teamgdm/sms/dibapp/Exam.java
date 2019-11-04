package it.teamgdm.sms.dibapp;

import androidx.annotation.NonNull;

import java.io.Serializable;

class Exam implements Serializable {
    int classID;
    String className;
    int classCode;
    String classDescription;
    int classYear;
    int classSemester;

    @NonNull
    public String toString() {
        return className;
    }
}