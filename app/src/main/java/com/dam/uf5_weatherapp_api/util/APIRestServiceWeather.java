package com.dam.uf5_weatherapp_api.util;

import com.dam.uf5_weatherapp_api.data.WeatherRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRestServiceWeather {
    public static final String BASE_URL = "https://api.darksky.net/forecast/";

    @GET("{key}/{latitud},{longitud}")
    Call<WeatherRes> obtenerTiempo(
            @Path("key") String key,
            @Path("latitud") double latitud,
            @Path("longitud") double longitud,
            @Query("exclude") String exclude,
            @Query("lang") String lang);
}