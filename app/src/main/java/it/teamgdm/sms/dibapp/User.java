package it.teamgdm.sms.dibapp;

import android.util.Log;

class User {

    private String name;
    private String surname;
    private String email;
    private int roleId;
    private String roleName;
    private int degreeCourseId;
    private String degreeCourseName;
    private long registrationDate;
    private long sessionExpiryTime;

    User() {

    }

    boolean isStudent() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isStudent-");
        return roleId == Settings.KEY_ROLE_STUDENT;
    }

    boolean isTeacher() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -isTeacher-");
        return roleId == Settings.KEY_ROLE_TEACHER;
    }

    String getName() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getName-");
        return name;
    }

    String getSurname() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getSurname-");
        return surname;
    }

    String getEmail() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getEmail-");
        return email;
    }

    long getRegistrationDate() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getRegistrationDate-");
        return registrationDate;
    }

    long getSessionExpiryTime() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getSessionExpiryTime-");
        return sessionExpiryTime;
    }

    int getRoleId() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getRoleId-");
        return roleId;
    }

    String getRoleName() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getRoleName-");
        return roleName;
    }

    int getDegreeCourseId() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getDegreeCourseId-");
        return degreeCourseId;
    }

    String getDegreeCourseName() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getDegreeCourseName-");
        return degreeCourseName;
    }

    void setName(String name) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setName-");
        this.name = name;
    }

    void setSurname(String surname) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSurname-");
        this.surname = surname;
    }

    void setEmail(String email) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setEmail-");
        this.email = email;
    }

    void setRegistrationDate(long registrationDate) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setRegistrationDate-");
        this.registrationDate = registrationDate;
    }

    void setSessionExpiryDate(long sessionExpiryTime) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setSessionExpiryDate-");
        this.sessionExpiryTime = sessionExpiryTime;
    }

    void setRoleId(int roleId) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setRoleId-");
        this.roleId = roleId;
    }

    void setRoleName(String roleName) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setRoleName-");
        this.roleName = roleName;
    }

    void setDegreeCourseId(int degreeCourseId) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setDegreeCourseId-");
        this.degreeCourseId = degreeCourseId;
    }

    void setDegreeCourseName(String degreeCourseName) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -setDegreeCourseName-");
        this.degreeCourseName = degreeCourseName;
    }
}