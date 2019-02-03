package com.csc.finalweather.data.model.server

data class DailyForecastResponseModel(var summary: String, var data: ArrayList<DailyItemModel> )