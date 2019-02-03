package com.csc.finalweather.data.model.server

data class CurrentForecastResponseModel(var time: Long,
                                        var summary: String,
                                        var icon: String,
                                        var precipProbability: Double,
                                        var temperature: Double,
                                        var apparentTemperature: Double,
                                        var humidity: Double,
                                        var pressure: Double,
                                        var windSpeed: Double)