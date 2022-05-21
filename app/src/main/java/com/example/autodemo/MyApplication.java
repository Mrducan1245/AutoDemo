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

public class MyApplication extends Application {
    private AlarmManager alarmManager;
    @Override
    public void onCreate() {
        super.onCreate();
        dateTimes = new ArrayList<>();
        setAlarmToStartApp();
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

    /**
     * 设置闹钟启动第三方APP
     */
    @SuppressLint("ShortAlarm")
    private void setAlarmToStartApp() {
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        // Date(int year, int month, int date, int hrs, int min, int sec)

        long startMills = System.currentTimeMillis()+30*1000;
        Log.e("Alarm","转化程mills时间是；"+startMills);
        Log.e("alarm",""+TimeTransformUtil.mills2Date(startMills));
        long intervalMills = 1000*15;
        Log.e("alarm","intervalMills"+TimeTransformUtil.mills2Date(intervalMills));

        Intent intent = new Intent(Intent.ACTION_MAIN);
        ComponentName componentName = new ComponentName("com.taobao.taobao","com.taobao.tao.TBMainActivity");
        intent.setComponent(componentName);

        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,startMills,intervalMills,pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,startMills,intervalMills,pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AutoBilily.alarmManager = alarmManager;
        }
    }


}
