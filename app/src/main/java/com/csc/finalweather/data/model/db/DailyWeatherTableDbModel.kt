package com.csc.finalweather.data.model.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DailyWeatherTableDbModel(@Id var id: Long = 0, var formattedTime: String, var summary: String,var icon: String, var temperatureMin: String, var temperatureMax: String, var humidity: String,  var windSpeed: String )
