package com.csc.finalweather.data.model.server

data class DailyItemModel(var time: Long,
                          var summary: String,
                          var icon: String,
                          var temperatureMin: Double,
                          var temperatureMax: Double,
                          var humidity: Double,
                          var pressure: Double,
                          var windSpeed: Double)