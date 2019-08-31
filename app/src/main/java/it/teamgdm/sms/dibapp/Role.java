package it.teamgdm.sms.dibapp;

import androidx.annotation.NonNull;

public class Role {

    private String name;

    Role(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
