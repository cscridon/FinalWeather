package com.csc.finalweather.data.net

import com.csc.finalweather.BuildConfig
import com.csc.finalweather.data.model.server.WeatherForecastResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.Call
import retrofit2.http.Path

interface WeatherApiHttpService {

    /////////////////////
    // Forecast Request
    ////////////////////
    @Headers("Accept: application/json")
    @GET("forecast/"+ BuildConfig.API_KEY+"/{lat},{long}?units=ca&exclude=minutely,hourly,alerts,flags")
    fun getWeatherForcastForLocation(@Path("lat") locationLat:Double, @Path("long") locationLong:Double): Call<WeatherForecastResponseModel>

}