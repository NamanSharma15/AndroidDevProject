package com.example.myapplication.models;

public class MedicineReminders {
    String name;
    String time;
    public MedicineReminders(String name,String time){
        this.name = name;
        this.time = time;
    }
    public String getName(){
        return  name;
    }
    public String getTime(){
        return  time;
    }
}
