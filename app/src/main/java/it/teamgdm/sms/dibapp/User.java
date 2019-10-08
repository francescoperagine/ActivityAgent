package it.teamgdm.sms.dibapp;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class User {

    private int ID;
    private String name;
    private String surname;
    private String ssn;
    private String degreeCourse;
    private String roleName;
    private String email;
    private int degreeCourseId;
    private String degreeCourseName;
    private Date registrationDate;
    private String password;
    private String confirmPassword;

    User() {

    }

    boolean isStudent() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isStudent-");
        return roleName.equals(R.string.studentRoleString);
    }

    boolean isTeacher() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isTeacher-");
        return roleName.equals(R.string.teacherRoleString);
    }

    int getID() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getID-");
        return ID;
    }

    void setID(int ID) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setID-");
        this.ID = ID;
    }

    String getName() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getName-");
        return name;
    }

    void setName(String name) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setName-");
        this.name = name;
    }

    String getSurname() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getSurname-");
        return surname;
    }

    void setSurname(String surname) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setSurname-");
        this.surname = surname;
    }

    String getSsn() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getSsn-");
        return ssn;
    }

    void setSsn(String ssn) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setSsn-");
        this.ssn = ssn;
    }

    String getDegreeCourse() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getDegreeCourse-");
        return degreeCourse;
    }

    void setDegreeCourse(String degreeCourse) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setDegreeCourse-");
        this.degreeCourse = degreeCourse;
    }

    String getRoleName() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getRoleName-");
        return roleName;
    }

    void setRole(String roleName) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setRole-");
        this.roleName = roleName;
    }

    String getEmail() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getEmail-");
        return email;
    }

    void setEmail(String email) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setEmail-");
        this.email = email;
    }

    String getPassword() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getPassword-");
        return password;
    }

    void setPassword(String password) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setPassword-");
        this.password = password;
    }

    String getConfirmPassword() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getConfirmPassword-");
        return confirmPassword;
    }

    void setConfirmPassword(String confirmPassword) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -confirmPassword-");
        this.confirmPassword = confirmPassword;
    }

    boolean isEmailValid() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -isEmailValid-");
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @NonNull
    public String toString() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -toString-");
        return name + Constants.KEY_BLANK + surname + Constants.KEY_BLANK + ssn + Constants.KEY_BLANK + roleName + Constants.KEY_BLANK + email + Constants.KEY_BLANK + password;
    }

    void setRegistrationDate(Date date) {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -setRegistrationDate-"+ date);
        registrationDate = date;
    }

    String getRegistrationDate() {
        Log.i(Constants.TAG, getClass().getSimpleName() + " -getRegistrationDate-");
        return new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.getDefault()).format(registrationDate);
    }
}