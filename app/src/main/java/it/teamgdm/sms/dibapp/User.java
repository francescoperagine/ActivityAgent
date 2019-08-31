package it.teamgdm.sms.dibapp;

import android.util.Log;

class User {

    private String name;
    private String surname;
    private String ssn;
    private String roleName;
    private String email;
    private int degreeCourseId;
    private String degreeCourseName;
    private long registrationDate;
    private long sessionExpiryTime;
    private String password;
    private String confirmPassword;

    User() {

    }

    boolean isStudent() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isStudent-");
        return roleName.equals(R.string.studentRoleString);
    }

    boolean isTeacher() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isTeacher-");
        return roleName.equals(R.string.teacherRoleString);
    }

    String getName() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getName-");
        return name;
    }

    void setName(String name) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setName-");
        this.name = name;
    }

    String getSurname() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getSurname-");
        return surname;
    }

    void setSurname(String surname) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSurname-");
        this.surname = surname;
    }

    String getSsn() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getSsn-");
        return ssn;
    }

    void setSsn(String ssn) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSsn-");
        this.ssn = ssn;
    }

    String getRole() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getRole-");
        return roleName;
    }

    void setRole(String roleName) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setRole-");
        this.roleName = roleName;
    }

    String getEmail() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getEmail-");
        return email;
    }

    void setEmail(String email) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setEmail-");
        this.email = email;
    }

    void setSessionExpiryDate(long sessionExpiryTime) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSessionExpiryDate-");
        this.sessionExpiryTime = sessionExpiryTime;
    }

    String getPassword() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getPassword-");
        return password;
    }

    void setPassword(String password) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setPassword-");
        this.password = password;
    }

    String getConfirmPassword() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getConfirmPassword-");
        return confirmPassword;
    }

    void setConfirmPassword(String confirmPassword) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -confirmPassword-");
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return name + Settings.KEY_BLANK + surname + Settings.KEY_BLANK + ssn + Settings.KEY_BLANK + roleName + Settings.KEY_BLANK + email + Settings.KEY_BLANK + password;
    }
}