package com.example.autodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class EditDateActivity extends AppCompatActivity implements View.OnClickListener {
    private TimePicker timePicker;
    private TextView tvShowWeek;
    private ImageButton ibDatePicker;
    private Button btnCancle,btnConfirm;
    private EditText etInputMills;

    private DateTime dateTime;
    private ArrayList<DateTime> dateTimes;
    private int hour,minute,year,month,dayOfMonth =0;
    private  int second = 10;

    private  MyApplication myApplication;
    private SetTImeFragment setTImeFragment;

    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_date);
        myApplication = (MyApplication) getApplication();
        dateTimes = myApplication.getDateTimes();
        setDefaultDateTime();
        bindView();
    }

    private void setDefaultDateTime() {
        LocalDateTime localDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.now();
            year = localDateTime.getYear();
            month = localDateTime.getMonthValue()-1;
            Log.e("setDefaultDateTime()","locatDateTime得出的月份时间是："+month);
            dayOfMonth = localDateTime.getDayOfMonth();
            hour = localDateTime.getHour();
            minute = localDateTime.getMinute();
        }
    }

    private void bindView() {
        timePicker = findViewById(R.id.timePicker);
        tvShowWeek = findViewById(R.id.tv_show_week);
        ibDatePicker = findViewById(R.id.ib_date_picker);
        btnCancle = findViewById(R.id.btn_cancle);
        btnConfirm= findViewById(R.id.btn_confirm);
        etInputMills = findViewById(R.id.et_input_mills);

        ibDatePicker.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        ibDatePicker.setOnClickListener(this);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }
                Log.e("测试","时间改变了，当前时间为："+ hour+":"+minute);
            }
        });
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_date_picker:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                    DatePickerDialog dpd = new DatePickerDialog(this);
                    dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            year = datePicker.getYear();
                            month = datePicker.getMonth();
                            Log.e("sonDateSet","onDateSet得出的月份时间是："+month);
                            dayOfMonth = datePicker.getDayOfMonth();
                        }
                    });
                    dpd.show();
                }

                break;
            case R.id.btn_cancle:
                finish();
                break;
            case R.id.btn_confirm:
                //获取当前时间数据并保存
                String strSecond = etInputMills.getText().toString();
                second = Integer.parseInt(strSecond);
                Log.e("mills","mills的值是"+second);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dateTime = new DateTime(hour,minute,year,month,dayOfMonth);
                    dateTime.setMills(second* 1000L);
                    Log.e("测试","得到了dateTime对象，对象为："+dateTime);
                    myApplication.getDateTimes().add(dateTime);
                    setTImeFragment = (SetTImeFragment) myApplication.getFragmentManager().findFragmentByTag("setTime");
                    setTImeFragment.timeAdapter.notifyDataSetChanged();
                }
                setAlarmToStartApp(second* 1000L);
                finish();
                break;
            case R.id.et_input_mills:
                etInputMills.requestFocus();
                break;
        }
    }

    /**
     * 设置闹钟启动第三方APP
     */
    @SuppressLint("ShortAlarm")
    private void setAlarmToStartApp(long intervalMills) {
        // Date(int year, int month, int date, int hrs, int min, int sec)
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth,hour,minute);

        long startMills = calendar.getTimeInMillis();//1000*60*60*2
        if (intervalMills == 0) intervalMills = 1000*10;//默认间隔延时是10s
        Intent intent = new Intent(Intent.ACTION_MAIN);
        ComponentName componentName = new ComponentName("com.taobao.taobao","com.taobao.tao.TBMainActivity");
        intent.setComponent(componentName);

        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,startMills,intervalMills,pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,startMills,pendingIntent);
        Log.e("alarm","闹钟启动时间是："+TimeTransformUtil.mills2Date(startMills)+",间隔时间是："+intervalMills);
        Log.e("alarm","当前正确时间是："+TimeTransformUtil.mills2Date(System.currentTimeMillis())+",间隔时间是："+intervalMills);
        AutoBilily.alarmManager = alarmManager;
        AutoBilily.startMills = startMills ;
        AutoBilily.intervalMills = intervalMills;
        AutoBilily.pendingIntent = pendingIntent;
    }
}