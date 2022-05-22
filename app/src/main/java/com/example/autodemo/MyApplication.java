package com.example.autodemo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;

public class MyApplication extends Application {

    public long getIntervalMills() {
        return intervalMills;
    }

    public void setIntervalMills(long intervalMills) {
        this.intervalMills = intervalMills;
    }

    private long intervalMills ;

    @Override
    public void onCreate() {
        super.onCreate();
        dateTimes = new ArrayList<>();
    }

    public ArrayList<DateTime> getDateTimes() {
        return dateTimes;
    }

    public void setDateTimes(ArrayList<DateTime> dateTimes) {
        this.dateTimes = dateTimes;
    }

    public ArrayList<DateTime> dateTimes;
    public FragmentManager fragmentManager;

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
