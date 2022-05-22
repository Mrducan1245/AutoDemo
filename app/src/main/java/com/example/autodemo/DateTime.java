package com.example.autodemo;

import java.util.Calendar;
import java.util.Date;

public class DateTime {

    private int minute;
    private int year;
    private int month;
    private int hour;
    private int dayOfMonth;
    private long mills;//延迟时间ms

    public void setMonth(int month) {
        this.month = month;
    }

    public long getMills() {
        return mills;
    }

    public void setMills(long mills) {
        this.mills = mills;
    }

    public DateTime(int hour, int minute, int year, int month,int dayOfMonth) {
        this.hour = hour;
        this.minute = minute;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(short month) {
        this.month = month;
    }


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    public DateTime( int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}
