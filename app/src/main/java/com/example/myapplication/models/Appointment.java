package com.example.myapplication.models;

public class Appointment {
    String date;
    String time;
    String userName;
    String userId;
    String doctorName;
    String doctorId;
    boolean isApproved;
    public Appointment(){}

    public Appointment(String date, String time, String userName, String userId, String doctorName, String doctorId, boolean isApproved) {
        this.date = date;
        this.time = time;
        this.userName = userName;
        this.userId = userId;
        this.doctorName = doctorName;
        this.doctorId = doctorId;
        this.isApproved = isApproved;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
