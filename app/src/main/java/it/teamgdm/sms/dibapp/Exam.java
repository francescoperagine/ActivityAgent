package it.teamgdm.sms.dibapp;

import androidx.annotation.NonNull;

import java.io.Serializable;

class Exam implements Serializable {
    int classID;
    String name;
    int code;
    String classDescription;
    int year;
    int semester;

    @NonNull
    public String toString() {
        return name;
    }
}