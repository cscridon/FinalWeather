package com.csc.finalweather.domain.mapper

import com.csc.finalweather.data.model.db.CurrentWeatherTableDbModel
import com.csc.finalweather.data.model.db.DailyWeatherTableDbModel
import com.csc.finalweather.data.model.server.CurrentForecastResponseModel
import com.csc.finalweather.data.model.server.DailyItemModel
import com.csc.finalweather.domain.types.UnitType
import com.csc.finalweather.presentation.utils.formatCelsius
import com.csc.finalweather.presentation.utils.formatKmh
import com.csc.finalweather.presentation.utils.formatWith2digitsWithPercentage
import com.csc.finalweather.presentation.utils.formatWithTimezone
import java.text.DecimalFormat

class ModelMapperHelper {

    companion object {

       fun mapCurrentForecastResponseToDbModel( locationName:String, receivedServerModel : CurrentForecastResponseModel) : CurrentWeatherTableDbModel {
           return CurrentWeatherTableDbModel(summary = receivedServerModel.summary,
                                             apparentTemperature = receivedServerModel.apparentTemperature.toString()+ UnitType.CELSIUS,
                                             humidity = receivedServerModel.humidity.formatWith2digitsWithPercentage(),
                                             icon = receivedServerModel.icon,
                                             locationName = locationName,
                                             temperature = receivedServerModel.temperature.toString()+ UnitType.CELSIUS,
                                             windSpeed = receivedServerModel.windSpeed.formatKmh() )
       }


        fun mapWeekForecastResponseToDbModel(  receivedServerModel : ArrayList<DailyItemModel>, receivedTimezone:String) : ArrayList<DailyWeatherTableDbModel> {
            val dbMappedList = arrayListOf<DailyWeatherTableDbModel>()
            for (dailyForecastItem in receivedServerModel) {
                dbMappedList.add(element = DailyWeatherTableDbModel(formattedTime = dailyForecastItem.time.formatWithTimezone(receivedTimezone),
                                                                    summary = dailyForecastItem.summary,
                                                                    icon = dailyForecastItem.icon,
                                                                    windSpeed = dailyForecastItem.windSpeed.formatKmh() ,
                                                                    humidity = dailyForecastItem.humidity.formatWith2digitsWithPercentage(),
                                                                    temperatureMin = dailyForecastItem.temperatureMin.formatCelsius(),
                                                                    temperatureMax = dailyForecastItem.temperatureMax.formatCelsius() ))
            }
            return dbMappedList
        }



    }
}