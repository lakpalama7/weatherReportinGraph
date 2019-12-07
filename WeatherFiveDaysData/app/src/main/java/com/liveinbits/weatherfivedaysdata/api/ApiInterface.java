package com.liveinbits.weatherfivedaysdata.api;

import com.liveinbits.weatherfivedaysdata.model.JsonObjectContainer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("data/2.5/forecast?")
    Call<JsonObjectContainer> getJsonObjectContainer(
            @Query("q") String city,
            @Query("APPID") String api_keyk,
            @Query("units") String metric
    );

}
