package it.teamgdm.sms.dibapp;

import java.util.Date;

public class User {

    private final String TAG = "dibApp.UserClass";

    String name;
    String surname;
    String email;
    Date sessionExpiryDate;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getName() { return name; }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}