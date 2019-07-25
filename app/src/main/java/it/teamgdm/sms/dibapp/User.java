package it.teamgdm.sms.dibapp;

import java.util.Date;

public class User {

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
        return true;
    }

    public boolean isTeacher() {
        return true;
    }

    public boolean isBackofficeOperator() {
        return true;
    }

    public String getName() { return name; }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegistrationDate() { return registrationDate; }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }

    public int getRoleId() { return roleId; }

    public String getRoleName() { return roleName; }

    public int getDegreeCourseId() { return degreeCourseId; }

    public String getDegreeCourseName() { return degreeCourseName; }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }

    public void setSessionExpiryDate(Date sessionExpiryDate) { this.sessionExpiryDate = sessionExpiryDate; }

    public void setRoleId(int roleId) { this.roleId = roleId; }

    public void setRoleName(String roleName) { this.roleName = roleName; }

    public void setDegreeCourseId(int degreeCourseId) { this.degreeCourseId = degreeCourseId; }

    public void setDegreeCourseName(String degreeCourseName) { this.degreeCourseName = degreeCourseName; }
}