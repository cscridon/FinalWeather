package com.csc.finalweather.data.model.server

data class WeatherForecastResponseModel(var latitude: Double,
                                        var longitude: Double,
                                        var timezone: String,
                                        var currently: CurrentForecastResponseModel,
                                        var daily: DailyForecastResponseModel,
                                        var offset: Int )