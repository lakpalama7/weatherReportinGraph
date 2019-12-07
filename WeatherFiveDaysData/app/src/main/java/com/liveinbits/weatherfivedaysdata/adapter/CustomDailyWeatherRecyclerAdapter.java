package com.liveinbits.weatherfivedaysdata.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.liveinbits.weatherfivedaysdata.R;
import com.liveinbits.weatherfivedaysdata.model.DailyWeatherReport;

import java.util.ArrayList;

public class CustomDailyWeatherRecyclerAdapter extends RecyclerView.Adapter<CustomDailyWeatherRecyclerAdapter.MyViewHolder> {

    private  CustomListener customListener;
    private Context context;
    private ArrayList<DailyWeatherReport> dailyWeatherReportlist;
    private int index=-1;
    public CustomDailyWeatherRecyclerAdapter(Context context, ArrayList<DailyWeatherReport> list){
        this.context=context;
        this.dailyWeatherReportlist=list;
        this.customListener= (CustomListener) context;
    }

    public interface CustomListener{
        void onClickItem(View view,int position);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.daily_weather_report,parent,false);
        return new MyViewHolder(view,customListener);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        DailyWeatherReport dailyReport=dailyWeatherReportlist.get(position);
        holder.txt_name_daily.setText(dailyReport.getDay_name());
        holder.img_daily.setImageResource(dailyReport.getIcon_id());
        holder.txt_temp_daily.setText(String.valueOf(dailyReport.getTemp()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=position;
                notifyDataSetChanged();
                holder.mylistener.onClickItem(holder.view,position);
            }
        });

        if(index==position){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#FF4081"));
            holder.txt_name_daily.setTextColor(Color.WHITE);
        }
        else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#00b0ff"));
            holder.txt_name_daily.setTextColor(Color.BLACK);
        }

    }

    @Override
    public int getItemCount() {
        return dailyWeatherReportlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private View view;
        TextView txt_name_daily;
        ImageView img_daily;
        TextView txt_temp_daily;
        private CustomListener mylistener;
        private LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView,CustomListener customListener) {
            super(itemView);
            this.view=itemView;
            txt_name_daily=view.findViewById(R.id.name_daily);
            img_daily=view.findViewById(R.id.img_daily);
            txt_temp_daily=view.findViewById(R.id.temp_daily);
            this.mylistener=customListener;
            linearLayout=view.findViewById(R.id.daily_linearlayout);


        }

    }
}
