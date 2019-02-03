package com.csc.finalweather.data.repository.datasource.local

import com.csc.finalweather.data.model.db.CurrentWeatherTableDbModel
import com.csc.finalweather.data.model.db.DailyWeatherTableDbModel
import com.csc.finalweather.data.model.server.LocationModel

interface LocalWeatherDataStore {

    fun getCurrentWeatherForecast(): CurrentWeatherTableDbModel?
    fun getDailyWeatherForecastList(): ArrayList<DailyWeatherTableDbModel>?

}