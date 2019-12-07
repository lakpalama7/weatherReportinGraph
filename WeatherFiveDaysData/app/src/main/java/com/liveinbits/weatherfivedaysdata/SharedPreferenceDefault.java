package com.liveinbits.weatherfivedaysdata;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceDefault {

    private SharedPreferences pref;
    public SharedPreferenceDefault(Activity activity){
        pref=activity.getPreferences(activity.MODE_PRIVATE);
    }
    public void setCity(String city){
        pref.edit().putString("city",city).commit();
    }
    public String getCity(){
        return pref.getString("city","Chittoor");
    }
}
