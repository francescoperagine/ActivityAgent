package it.teamgdm.sms.dibapp;

import androidx.annotation.NonNull;

public class SpinnerElement {

    private String name;

    SpinnerElement(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
