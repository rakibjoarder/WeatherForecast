package com.aust.rakib.weatherforecast.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Personal on 7/12/2017.
 */

public interface WeatherApiService {
    @GET()
    Call<WeatherForecastResponse>weatherForecastResponseCall(@Url String urlString);
}
