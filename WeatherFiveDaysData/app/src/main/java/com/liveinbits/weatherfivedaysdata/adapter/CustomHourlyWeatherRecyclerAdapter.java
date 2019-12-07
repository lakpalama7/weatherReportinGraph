package com.liveinbits.weatherfivedaysdata.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liveinbits.weatherfivedaysdata.R;
import com.liveinbits.weatherfivedaysdata.model.HourlyWeatherReport;

import java.util.ArrayList;

public class CustomHourlyWeatherRecyclerAdapter extends RecyclerView.Adapter<CustomHourlyWeatherRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HourlyWeatherReport> hourlyWeatherReportslist;
   public CustomHourlyWeatherRecyclerAdapter(Context context, ArrayList<HourlyWeatherReport> list){
       this.context=context;
       this.hourlyWeatherReportslist=list;
   }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.hourly_weather,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       HourlyWeatherReport hourlyWeatherReport=hourlyWeatherReportslist.get(position);
       holder.txt_hour_daily.setText(hourlyWeatherReport.getHour());
       holder.img_daily.setImageResource(hourlyWeatherReport.getIcon_id());
       holder.txt_temp_daily.setText(hourlyWeatherReport.getTemp()+"°C");
    }

    @Override
    public int getItemCount() {
        return hourlyWeatherReportslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView txt_hour_daily;
       ImageView img_daily;
       TextView txt_temp_daily;

        public MyViewHolder(@NonNull View view) {
            super(view);
            txt_hour_daily=view.findViewById(R.id.hour_daily);
            img_daily=view.findViewById(R.id.img_daily);
            txt_temp_daily=view.findViewById(R.id.temp_daily);
        }
    }
}
