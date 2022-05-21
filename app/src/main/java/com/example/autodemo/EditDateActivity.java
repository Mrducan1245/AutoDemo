package com.example.autodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class EditDateActivity extends AppCompatActivity implements View.OnClickListener {
    private TimePicker timePicker;
    private TextView tvShowWeek;
    private ImageButton ibDatePicker;
    private Button btnCancle,btnConfirm;

    private DateTime dateTime;
    private ArrayList<DateTime> dateTimes;
    private int hour,minute;

    private  MyApplication myApplication;
    private SetTImeFragment setTImeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_date);
        myApplication = (MyApplication) getApplication();
        dateTimes = myApplication.getDateTimes();
        bindView();
    }

    private void bindView() {
        timePicker = findViewById(R.id.timePicker);
        tvShowWeek = findViewById(R.id.tv_show_week);
        ibDatePicker = findViewById(R.id.ib_date_picker);
        btnCancle = findViewById(R.id.btn_cancle);
        btnConfirm= findViewById(R.id.btn_confirm);

        ibDatePicker.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        ibDatePicker.setOnClickListener(this);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
                Log.e("测试","时间改变了，当前时间为："+i+":"+i1);

            }
        });
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_date_picker:
                break;
            case R.id.btn_cancle:
                finish();
                break;
            case R.id.btn_confirm:
                //获取当前时间数据并保存
                dateTime = new DateTime(hour,minute);
                Log.e("测试","得到了dateTime对象，对象为："+dateTime);
                myApplication.getDateTimes().add(dateTime);
                setTImeFragment = (SetTImeFragment) myApplication.getFragmentManager().findFragmentByTag("setTime");
                setTImeFragment.timeAdapter.notifyDataSetChanged();
                finish();
                break;
        }
    }
}