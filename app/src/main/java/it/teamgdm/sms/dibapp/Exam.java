package it.teamgdm.sms.dibapp;

import androidx.annotation.NonNull;

import java.io.Serializable;

class Exam implements Serializable {
    int ID;
    String name;
    int code;
    String classDescription;

    @NonNull
    public String toString() {
        return name;
    }
}