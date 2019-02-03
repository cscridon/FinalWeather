package com.csc.finalweather.data.repository.datasource.local

import com.csc.finalweather.data.cache.WeatherCache
import com.csc.finalweather.data.model.db.CurrentWeatherTableDbModel
import com.csc.finalweather.data.model.db.DailyWeatherTableDbModel

class  LocalWeatherDataStoreImpl( val weatherCache: WeatherCache):LocalWeatherDataStore {


    override fun getCurrentWeatherForecast(): CurrentWeatherTableDbModel? {
        return weatherCache.getCurrentWeatherForecast()
    }

    override fun getDailyWeatherForecastList(): ArrayList<DailyWeatherTableDbModel>? {
        return  weatherCache.getWeeklyWeatherForecast()
    }


}