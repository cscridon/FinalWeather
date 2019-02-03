package com.csc.finalweather.data.repository

import com.csc.finalweather.data.model.db.CurrentWeatherTableDbModel
import com.csc.finalweather.data.model.db.DailyWeatherTableDbModel
import com.csc.finalweather.data.model.server.LocationModel
import com.csc.finalweather.data.repository.datasource.WeatherDataStoreFactory

class WeatherDataRepository(var weatherDataStoreFactory: WeatherDataStoreFactory) {

    //requests all weather data from server
    fun downloadWeatherForecastForLocationAndCacheIt(locationModel:LocationModel){
        weatherDataStoreFactory.getRemoteWeatherDataStore().downloadWeatherForecastForLocationAndCacheIt(locationModel)
    }

    //retrieves Current Weather data
    fun getCurrentWeatherForecast(): CurrentWeatherTableDbModel? {
        return weatherDataStoreFactory.getLocalWeatherDataStore().getCurrentWeatherForecast()
    }

    //retrieves Daily Forecast data for this Week
    fun getDailyWeatherForecastList(): ArrayList<DailyWeatherTableDbModel>? {
        return weatherDataStoreFactory.getLocalWeatherDataStore().getDailyWeatherForecastList()
    }


}