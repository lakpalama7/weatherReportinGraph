package com.liveinbits.weatherfivedaysdata.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.liveinbits.weatherfivedaysdata.R;
import com.liveinbits.weatherfivedaysdata.adapter.CustomHourlyWeatherRecyclerAdapter;
import com.liveinbits.weatherfivedaysdata.model.HourlyWeatherReport;
import com.liveinbits.weatherfivedaysdata.model.JsonObjectContainer;
import com.liveinbits.weatherfivedaysdata.model.WeatherList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DailyWeatherReportFourthFragment extends Fragment {

    private Context context;
    private TextView txt_datetime;
    private  int position;
    private RecyclerView recyclerView;
    private JsonObjectContainer json;
    private BarChart barChart_daily;
    private LineChart lineChart_daily;
    private boolean status=false;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_daily_weather_report, container, false);
        txt_datetime=view.findViewById(R.id.date_time);

        recyclerView=view.findViewById(R.id.hourly_weather_day);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);

        barChart_daily=view.findViewById(R.id.daily_bar_char_day);
        lineChart_daily=view.findViewById(R.id.daily_report_linechart);

        showHourlyWeatherReport(position);
        return view;
    }

    public void setDailyData(Context context,int id,JsonObjectContainer json){
        position=id;
        this.context=context;
        this.json=json;
    }



    private void showHourlyWeatherReport(int position) {
        status=true;
        int counter=0;
        int length=json.getWeatherListList().size();
        int tempcounter=-1;
        while(counter<length){
            WeatherList weatherList=json.getWeatherListList().get(counter);
            Long dt=weatherList.getDt();
            String hour=new SimpleDateFormat("hha", Locale.getDefault()).format(new Date(dt*1000));
            if(hour.equals("02AM")){
                tempcounter++;
                if(position==tempcounter){
                    getWeatherInfo(counter);
                }

            }
            counter++;

        }

    }



    private void getWeatherInfo(int counter) {
        int start_index=counter;
        int end_index=counter+8;
        int temp_counter=0;

        ArrayList<HourlyWeatherReport> hourlyWeatherReports=new ArrayList<>();
        ArrayList<BarEntry> hour_templist=new ArrayList();
        ArrayList<String> hourlist=new ArrayList();
        ArrayList<Entry> entries=new ArrayList();

        Long next_dt=json.getWeatherListList().get(counter).getDt();
        String datetime=new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault()).format(new Date(next_dt*1000));
        txt_datetime.setText(datetime);

        while(start_index<end_index){
            WeatherList weatherList= json.getWeatherListList().get(start_index);
            Long dt=weatherList.getDt();
            String hour=new SimpleDateFormat("hha", Locale.getDefault()).format(new Date(dt*1000));

            String temp=String.valueOf(weatherList.getMain().getTemp());
            String icon="w"+weatherList.getWeather().get(0).getIcon();
            int icon_id= context.getResources().getIdentifier(icon,"mipmap",context.getPackageName());
            if(icon_id>0){
                hourlyWeatherReports.add(new HourlyWeatherReport(hour, temp, icon_id));
                hour_templist.add(new BarEntry(Float.valueOf(temp),temp_counter));
                entries.add(new Entry(Float.parseFloat(temp), temp_counter));
                hourlist.add(hour);
                temp_counter++;
            }
            start_index++;
        }
        //display data into bar chart
        setBarChartInfo(hour_templist,hourlist);

        //display data into line chart
        setLineChartInfo(entries,hourlist);

        //set adapter into recyclerview.....call customAdapter
        CustomHourlyWeatherRecyclerAdapter adapter=new CustomHourlyWeatherRecyclerAdapter(getContext(),hourlyWeatherReports);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private void setLineChartInfo(ArrayList<Entry> entries, ArrayList<String> hourlist) {
        LineDataSet lineDataSet=new LineDataSet(entries,"Hourly Forecast Report");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleColor(Color.YELLOW);
        lineDataSet.setCircleRadius(3);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(8);

        LineData lineData=new LineData(hourlist,lineDataSet);

        lineChart_daily.setData(lineData);
        lineChart_daily.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart_daily.getXAxis().setTextColor(Color.BLACK);
        lineChart_daily.getXAxis().setTextSize(6);
        lineChart_daily.animateY(1000);

        lineChart_daily.setDescription(null);
        lineChart_daily.invalidate();

    }

    private void setBarChartInfo(ArrayList<BarEntry> hour_templist, ArrayList<String> hourlist) {
        BarDataSet barDataSet=new BarDataSet(hour_templist,"Hourly Forecast Report");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextSize(8);
        BarData barData=new BarData(hourlist,barDataSet);

        barChart_daily.setData(barData);
        barChart_daily.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart_daily.getXAxis().setTextSize(6);
        barChart_daily.getXAxis().setTextColor(Color.BLACK);
        barChart_daily.setDescription(null);
        barChart_daily.invalidate();
    }
}

