package com.csc.finalweather.data.model.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class CurrentWeatherTableDbModel(@Id var id: Long = 0,
                                       var summary: String,
                                       var icon: String ,
                                       var temperature: String,
                                       var apparentTemperature:String,
                                       var locationName: String,
                                       var humidity:String,
                                       var windSpeed:String)
