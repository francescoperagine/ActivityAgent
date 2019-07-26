package it.teamgdm.sms.dibapp;

import android.util.Log;

import java.util.Date;

class User {

    private final String TAG = "dibApp.UserClass";

    private String name;
    private String surname;
    private String email;
    private int roleId;
    private String roleName;
    private int degreeCourseId;
    private String degreeCourseName;
    private Date registrationDate;
    private Date sessionExpiryDate;

    public boolean isStudent() {
        Log.i(TAG, getClass().getSimpleName() + " -isStudent-");
        //TODO: user-isStudent()
        return true;
    }

    public boolean isTeacher() {
        Log.i(TAG, getClass().getSimpleName() + " -isTeacher-");
        //TODO: user-isTeacher()
        return true;
    }

    public boolean isBackofficeOperator() {
        Log.i(TAG, getClass().getSimpleName() + " -isBackofficeOperator-");
        //TODO: user-isBackofficeOperator()
        return true;
    }

    public String getName() {
        Log.i(TAG, getClass().getSimpleName() + " -getName-");
        return name;
    }

    public String getSurname() {
        Log.i(TAG, getClass().getSimpleName() + " -getSurname-");
        return surname;
    }

    public String getEmail() {
        Log.i(TAG, getClass().getSimpleName() + " -getEmail-");
        return email;
    }

    public Date getRegistrationDate() {
        Log.i(TAG, getClass().getSimpleName() + " -getRegistrationDate-");
        return registrationDate;
    }

    public Date getSessionExpiryDate() {
        Log.i(TAG, getClass().getSimpleName() + " -getSessionExpiryDate-");
        return sessionExpiryDate;
    }

    public int getRoleId() {
        Log.i(TAG, getClass().getSimpleName() + " -getRoleId-");
        return roleId;
    }

    public String getRoleName() {
        Log.i(TAG, getClass().getSimpleName() + " -getRoleName-");
        return roleName;
    }

    public int getDegreeCourseId() {
        Log.i(TAG, getClass().getSimpleName() + " -getDegreeCourseId-");
        return degreeCourseId;
    }

    public String getDegreeCourseName() {
        Log.i(TAG, getClass().getSimpleName() + " -getDegreeCourseName-");
        return degreeCourseName;
    }

    public void setName(String name) {
        Log.i(TAG, getClass().getSimpleName() + " -setName-");
        this.name = name;
    }

    public void setSurname(String surname) {
        Log.i(TAG, getClass().getSimpleName() + " -setSurname-");
        this.surname = surname;
    }

    public void setEmail(String email) {
        Log.i(TAG, getClass().getSimpleName() + " -setEmail-");
        this.email = email;
    }

    public void setRegistrationDate(Date registrationDate) {
        Log.i(TAG, getClass().getSimpleName() + " -setRegistrationDate-");
        this.registrationDate = registrationDate;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        Log.i(TAG, getClass().getSimpleName() + " -setSessionExpiryDate-");
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public void setRoleId(int roleId) {
        Log.i(TAG, getClass().getSimpleName() + " -setRoleId-");
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        Log.i(TAG, getClass().getSimpleName() + " -setRoleName-");
        this.roleName = roleName;
    }

    public void setDegreeCourseId(int degreeCourseId) {
        Log.i(TAG, getClass().getSimpleName() + " -setDegreeCourseId-");
        this.degreeCourseId = degreeCourseId;
    }

    public void setDegreeCourseName(String degreeCourseName) {
        Log.i(TAG, getClass().getSimpleName() + " -setDegreeCourseName-");
        this.degreeCourseName = degreeCourseName;
    }
}