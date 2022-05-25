package com.example.autodemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SetTImeFragment extends Fragment {
    private ImageButton ibAddTask,ibMore;
    private RecyclerView rvTimeList;

    private MyApplication myApplication;
    private ArrayList<DateTime> dateTimes;

    public TimeAdapter timeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
        dateTimes = myApplication.getDateTimes();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_time,container,false);
        binView(view);
        return view;
    }

    private void binView(View view) {
        ibAddTask = view.findViewById(R.id.ib_add_task);
        ibMore = view.findViewById(R.id.ib_more);
        rvTimeList = view.findViewById(R.id.rv_time_list);

        timeAdapter = new TimeAdapter(dateTimes);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false);
        rvTimeList.setLayoutManager(layoutManager);
        rvTimeList.setAdapter(timeAdapter);

        ibAddTask.setOnClickListener(new ClickListner());
        ibMore.setOnClickListener(new ClickListner());
    }

    private static class ClickListner implements View.OnClickListener{

        @SuppressLint({"NonConstantResourceId", "ResourceType", "RtlHardcoded"})
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ib_add_task:
                    Intent intent = new Intent(view.getContext(),EditDateActivity.class);
                    view.getContext().startActivity(intent);
                    break;
                case R.id.ib_more:
                    break;
            }
        }
    }
}
