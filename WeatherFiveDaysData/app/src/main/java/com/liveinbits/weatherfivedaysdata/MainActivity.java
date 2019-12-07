
package com.liveinbits.weatherfivedaysdata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.liveinbits.weatherfivedaysdata.adapter.CustomDailyWeatherRecyclerAdapter;
import com.liveinbits.weatherfivedaysdata.adapter.CustomHourlyWeatherRecyclerAdapter;
import com.liveinbits.weatherfivedaysdata.api.ApiClient;
import com.liveinbits.weatherfivedaysdata.api.ApiInterface;
import com.liveinbits.weatherfivedaysdata.fragment.DailyWeatherReportFifthFragment;
import com.liveinbits.weatherfivedaysdata.fragment.DailyWeatherReportFirstFragment;
import com.liveinbits.weatherfivedaysdata.fragment.DailyWeatherReportFourthFragment;
import com.liveinbits.weatherfivedaysdata.fragment.DailyWeatherReportSecondFragment;
import com.liveinbits.weatherfivedaysdata.fragment.DailyWeatherReportThirdFragment;
import com.liveinbits.weatherfivedaysdata.model.DailyWeatherReport;
import com.liveinbits.weatherfivedaysdata.model.HourlyWeatherReport;
import com.liveinbits.weatherfivedaysdata.model.JsonObjectContainer;
import com.liveinbits.weatherfivedaysdata.model.Weather;
import com.liveinbits.weatherfivedaysdata.model.WeatherList;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CustomDailyWeatherRecyclerAdapter.CustomListener {

    private TextView txt_city_name;
    private TextView txt_updated_datetime;
    private ImageView img_setting;
    private ImageView img_current_tmp;
    private TextView txt_current_tmp;
    private TextView txt_current_minmax_tmp;
    private TextView txt_current_tmp_desc;
    private TextView txt_sunrise;
    private TextView txt_sunset;
    private TextView txt_windspeed;
    private TextView txt_windpressure;
    private TextView txt_humidity;
    private TextView txt_coordinate;

    private RecyclerView daily_recyclerView;
    private RecyclerView hourly_recyclerView;
    private static final String APIKEY = "113a769afa7a35ebd95eef960ebf9209";
    private Handler handler;

    private BarChart barChart;
    private LineChart lineChart;

    private JsonObjectContainer json=null;


    public MainActivity(){
        handler=new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_city_name=findViewById(R.id.city_name);
        txt_updated_datetime=findViewById(R.id.updated_datetime);
        img_setting=findViewById(R.id.setting);
        img_current_tmp=findViewById(R.id.current_tmp_img);
        txt_current_tmp=findViewById(R.id.current_tmp);
        txt_current_minmax_tmp=findViewById(R.id.current_minmax_tmp);
        txt_current_tmp_desc=findViewById(R.id.current_tmp_desc);
        txt_sunrise=findViewById(R.id.sunrise_time);
        txt_sunset=findViewById(R.id.sunset_time);
        txt_windspeed=findViewById(R.id.windspeed);
        txt_windpressure=findViewById(R.id.windpressure);
        txt_humidity=findViewById(R.id.humidity);
        txt_coordinate=findViewById(R.id.coordinate);

        barChart=findViewById(R.id.daily_bar_char_day);
        lineChart=findViewById(R.id.daily_report_linechart);

        daily_recyclerView=findViewById(R.id.daily_weather);
        hourly_recyclerView=findViewById(R.id.hourly_weather);

        hourly_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        hourly_recyclerView.setHasFixedSize(true);

        daily_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        daily_recyclerView.setHasFixedSize(true);

        final SharedPreferenceDefault pref=new SharedPreferenceDefault(MainActivity.this);
        LoadJson(pref.getCity());

        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("lgo ","clicked ");
                showDialog(pref);
            }
        });




    }

    private void showDialog(final SharedPreferenceDefault pref) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Enter city name....");
        final EditText input=new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pref.setCity(input.getText().toString());
                LoadJson(pref.getCity());
            }
        });
        builder.show();
    }

    private void LoadJson(String city) {
        ApiInterface apiInterface=ApiClient.getApiClient().create(ApiInterface.class);

        Call<JsonObjectContainer> jsonObjectContainer=null;
        jsonObjectContainer=apiInterface.getJsonObjectContainer(city,APIKEY,"metric");

        jsonObjectContainer.enqueue(new Callback<JsonObjectContainer>() {
            @Override
            public void onResponse(Call<JsonObjectContainer> call, Response<JsonObjectContainer> response) {
                if(!response.isSuccessful()){
                    Log.v("not success" ,"why");
                }
                json=response.body();
               if(json!=null){
                   showDataInView(json);
               }
               else{
                   Toast.makeText(MainActivity.this,"No data available",Toast.LENGTH_LONG).show();
               }
            }

            @Override
            public void onFailure(Call<JsonObjectContainer> call, Throwable t) {
                Log.v("Error : ",t.getMessage());
            }
        });

    }

    private void showDataInView(final JsonObjectContainer json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        WeatherList list=json.getWeatherListList().get(0);
                        Weather weather=list.getWeather().get(0);
                        txt_city_name.setText(json.getCity().getName().concat(","+json.getCity().getCountry()));
                        Long current_date=list.getDt();
                        Long instant_date=System.currentTimeMillis();
                        String datetime=new SimpleDateFormat("E, dd MMM yyyy HH:mm a", Locale.getDefault()).format(new Date());
                        txt_updated_datetime.setText(datetime);
                        String icon="w".concat(weather.getIcon());
                        int icon_id=getResources().getIdentifier(icon,"mipmap",getPackageName());
                        if(icon_id>0) {
                            img_current_tmp.setImageResource(icon_id);
                        }
                        txt_current_tmp.setText(String.valueOf(list.getMain().getTemp()).concat("°C"));

                        //get min max temp
                        String mintemp=String.valueOf(list.getMain().getTemp_min());
                        String maxtemp=String.valueOf(list.getMain().getTemp_max());
                        txt_current_minmax_tmp.setText(mintemp.concat("°C")+ maxtemp.concat("°C"));

                        txt_current_tmp_desc.setText(weather.getDescription());

                        Long sunrise=json.getCity().getSunrise();
                        Long sunset=json.getCity().getSunset();
                        txt_sunrise.setText(new SimpleDateFormat("HH:mm a",Locale.ENGLISH).format(new Date(sunrise*1000)));
                        txt_sunset.setText(new SimpleDateFormat("HH:mm a",Locale.ENGLISH).format(new Date(sunset*1000)));

                        txt_windspeed.setText(String.valueOf(list.getWind().getSpeed()).concat("m/s"));
                        txt_windpressure.setText(String.valueOf(list.getMain().getPressure()).concat("hpa"));
                        txt_humidity.setText(String.valueOf(list.getMain().getHumidity()).concat(" %"));

                        //get lat lon coordinate
                        double lat=json.getCity().getCoord().getLat();
                        double lon=json.getCity().getCoord().getLon();
                        DecimalFormat df=new DecimalFormat("#.##");
                        txt_coordinate.setText(String.valueOf(df.format(lat))+","+String.valueOf(df.format(lon)));

                        showDailyWeatherReport(json);
                        showHourlyWeatherReport(json);

                    }
                });
            }
        }).start();
    }

    private void showHourlyWeatherReport(JsonObjectContainer json) {
         int counter=0;
        ArrayList<HourlyWeatherReport> hourlyWeatherReports=new ArrayList<>();
        ArrayList<BarEntry> templist=new ArrayList();
        ArrayList<String> hourlist=new ArrayList();

        while(counter<=7){
            WeatherList weatherList=json.getWeatherListList().get(counter);
            Long dt=weatherList.getDt();
            String hour=new SimpleDateFormat("hha", Locale.getDefault()).format(new Date(dt*1000));
            //String chart_hour=new SimpleDateFormat("HH:mm",Locale.ENGLISH).format(new Date(dt*1000));
            String temp=String.valueOf(weatherList.getMain().getTemp());
            String icon="w"+weatherList.getWeather().get(0).getIcon();
            int icon_id=getResources().getIdentifier(icon,"mipmap",getPackageName());
            if(icon_id>0){

                if(counter==0){
                    hourlyWeatherReports.add(new HourlyWeatherReport("Now",temp,icon_id));
                    templist.add(new BarEntry(Float.valueOf(temp),counter));
                    hourlist.add("Now");
                }
                else {
                    hourlyWeatherReports.add(new HourlyWeatherReport(hour, temp, icon_id));
                    templist.add(new BarEntry(Float.valueOf(temp),counter));
                    hourlist.add(hour);
                }
            }
            counter++;

        }


        BarDataSet barDataSet1=new BarDataSet(templist,"Hourly Forecast Report");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet1.setValueTextSize(8);
        BarData barData1=new BarData(hourlist,barDataSet1);
        barChart.setData(barData1);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setTextSize(6);
        barChart.getXAxis().setTextColor(Color.BLACK);
        barChart.setDescription(null);
       // barChart.setVisibleXRange(1,7); //dispaly number of bars 1 to 8
        barChart.invalidate();


        CustomHourlyWeatherRecyclerAdapter adapter=new CustomHourlyWeatherRecyclerAdapter(MainActivity.this,hourlyWeatherReports);
        adapter.notifyDataSetChanged();
        hourly_recyclerView.setAdapter(adapter);


    }

    private void showDailyWeatherReport(JsonObjectContainer json) {
        int length=json.getWeatherListList().size();
        int counter=0;
        ArrayList<DailyWeatherReport> dailyWeatherReportlist=new ArrayList<>();

        ArrayList<Entry> entries=new ArrayList();
        ArrayList days=new ArrayList();
        int i=0;
        while(counter<length){
            WeatherList weatherList=json.getWeatherListList().get(counter);
            Long dt=weatherList.getDt();
            String datetime=new SimpleDateFormat("HH:mm a",Locale.getDefault()).format(new Date(dt*1000));
            if(datetime.equals("02:30 AM")){
                String day=new SimpleDateFormat("EE",Locale.ENGLISH).format(new Date(dt*1000));
                double temp=weatherList.getMain().getTemp();

                String icon="w"+weatherList.getWeather().get(0).getIcon();
                int icon_id=getResources().getIdentifier(icon,"mipmap",getPackageName());
                if(icon_id>0){

                    dailyWeatherReportlist.add(new DailyWeatherReport(day,temp,icon_id));
                    entries.add(new Entry((float)temp, i));
                    i++;

                    days.add(day);
                }

            }
            counter++;

        }


        LineDataSet lineDataSet=new LineDataSet(entries,"Daily Forecast Report");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleColor(Color.YELLOW);
        lineDataSet.setCircleRadius(3);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(8);

        LineData lineData=new LineData(days,lineDataSet);

        lineChart.setData(lineData);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setTextColor(Color.BLACK);
        lineChart.getXAxis().setTextSize(8);
        lineChart.animateY(1000);
        lineChart.setDescription(null);
        lineChart.invalidate();

        CustomDailyWeatherRecyclerAdapter adapter=new CustomDailyWeatherRecyclerAdapter(MainActivity.this,dailyWeatherReportlist);
        adapter.notifyDataSetChanged();
        daily_recyclerView.setAdapter(adapter);

    }


    @Override
    public void onClickItem(View view, int position) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        switch (position){
            case 0:
                DailyWeatherReportFirstFragment dailyWeatherReportFirstFragment =new DailyWeatherReportFirstFragment();
                dailyWeatherReportFirstFragment.setDailyData(MainActivity.this,position,json);
                transaction.replace(R.id.framelayout, dailyWeatherReportFirstFragment,"DailyWeatherFragment").commit();
                break;
            case 1:
                DailyWeatherReportSecondFragment dailyWeatherReportSecondFragment =new DailyWeatherReportSecondFragment();
                dailyWeatherReportSecondFragment.setDailyData(MainActivity.this,position,json);
                transaction.replace(R.id.framelayout, dailyWeatherReportSecondFragment,"DailyWeatherFragment").commit();
                break;
            case 2:
                DailyWeatherReportThirdFragment dailyWeatherReportThirdFragment =new DailyWeatherReportThirdFragment();
                dailyWeatherReportThirdFragment.setDailyData(MainActivity.this,position,json);
                transaction.replace(R.id.framelayout, dailyWeatherReportThirdFragment,"DailyWeatherFragment").commit();
                break;
            case 3:
                DailyWeatherReportFourthFragment dailyWeatherReportFourthFragment =new DailyWeatherReportFourthFragment();
                dailyWeatherReportFourthFragment.setDailyData(MainActivity.this,position,json);
                transaction.replace(R.id.framelayout, dailyWeatherReportFourthFragment,"DailyWeatherFragment").commit();
                break;
            case 4:
                DailyWeatherReportFifthFragment dailyWeatherReportfifthFragment =new DailyWeatherReportFifthFragment();
                dailyWeatherReportfifthFragment.setDailyData(MainActivity.this,position,json);
                transaction.replace(R.id.framelayout, dailyWeatherReportfifthFragment,"DailyWeatherFragment").commit();
                break;
        }

    }
}
