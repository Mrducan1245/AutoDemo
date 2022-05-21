package com.example.autodemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rgActiveMain;
    private RadioButton rbHome,rbList;

    //Fragment object
    private HomeFragment homeFragment;
    private SetTImeFragment setTImeFragment;
    private FragmentManager fragmentManager;

    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApplication = (MyApplication) getApplication();
        fragmentManager = getSupportFragmentManager();
        myApplication.setFragmentManager(fragmentManager);
        bindView();
    }


    private void bindView() {
        rgActiveMain = findViewById(R.id.rg_actvt_main);
        rbHome = findViewById(R.id.rb_honme);
        rbList = findViewById(R.id.rb_list);
        //默认让此按钮被点击状态
        rbHome.setChecked(true);
        rgActiveMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                hideAllFragment(fragmentTransaction);
                switch (i){
                    case R.id.rb_honme:
                        if (homeFragment == null){
                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.conainer_fram,homeFragment);
                        }else {
                            fragmentTransaction.show(homeFragment);
                        }
                        break;
                    case R.id.rb_list:
                        if (setTImeFragment == null){
                            setTImeFragment = new SetTImeFragment();
                            fragmentTransaction.add(R.id.conainer_fram,setTImeFragment,"setTime");
                        }else {
                            fragmentTransaction.show(setTImeFragment);
                        }
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment!=null) fragmentTransaction.hide(homeFragment);
        if (setTImeFragment!=null) fragmentTransaction.hide(setTImeFragment);
    }

}