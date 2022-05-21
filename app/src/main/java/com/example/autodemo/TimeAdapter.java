package com.example.autodemo;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    public TimeAdapter(ArrayList<DateTime> dateTimes) {
        this.dateTimes = dateTimes;
    }

    private ArrayList<DateTime> dateTimes;

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_view,null,false);
        return new TimeViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        DateTime dateTime = dateTimes.get(position);
        holder.tvNoonAterNoon.setText(dateTime.getHour()>12?"下午":"上午");
        holder.tvClockTime.setText(dateTime.getHour()+":"+dateTime.getMinute());
    }


    @Override
    public int getItemCount() {
        return dateTimes.size();
    }

     class TimeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClockTime,tvNoonAterNoon,tvDate;
        private Switch swithcExcute;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClockTime = itemView.findViewById(R.id.tv_clock_time);
            tvNoonAterNoon = itemView.findViewById(R.id.tv_noon_afternoon);
            tvDate = itemView.findViewById(R.id.tv_date);
            swithcExcute = itemView.findViewById(R.id.switch_excute);
        }
    }

    //新增item
    public void addData(int pos,DateTime dateTime){
        dateTimes.add(dateTime);
        notifyItemInserted(pos);
    }

    //移除item
    public void deleateData(int pos){
        dateTimes.remove(pos);
        notifyItemRemoved(pos);
    }
}
