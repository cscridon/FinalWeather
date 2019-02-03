package com.csc.finalweather.presentation.presenter

import android.app.Activity
import android.graphics.drawable.Drawable
import com.csc.finalweather.R
import com.csc.finalweather.data.model.db.DailyWeatherTableDbModel
import com.csc.finalweather.domain.types.IconType

class ForecastPresenter(private var forecastView: SampleView?) {


    fun setCurrentLocationName(text: String){
        forecastView?.setCurrentLocationName(text)
    }
    fun setCurrentForecastSummary(text: String){
        forecastView?.setCurrentForecastSummary(text)
    }
    fun setCurrentForecastTemperature(text: String){
        forecastView?.setCurrentForecastTemperature(text)
    }
    fun setCurrentForecastFeelsLike(text: String){
        forecastView?.setCurrentForecastFeelsLike(text)
    }
    fun setCurrentForecastWindspeed(text: String){
        forecastView?.setCurrentForecastWindspeed(text)
    }
    fun setCurrentForecastTemperatureIconByName(iconName: String){
        forecastView?.setCurrentForecastTemperatureIconByName(iconName)
    }

    fun setDailyWeatherDataList(dailyData: ArrayList<DailyWeatherTableDbModel>){
        forecastView?.setDailyWeatherDataList(dailyData)
    }


    fun destroy() {
        forecastView = null
    }

    interface SampleView {
        fun setCurrentLocationName(text: String)
        fun setCurrentForecastSummary(text: String)
        fun setCurrentForecastTemperature(text: String)
        fun setCurrentForecastTemperatureIconByName(iconName: String)
        fun setCurrentForecastFeelsLike(text: String)
        fun setCurrentForecastWindspeed(text: String)
        fun setDailyWeatherDataList(dailyData: ArrayList<DailyWeatherTableDbModel>)
    }

}


fun getDrawableByWeatherIconName(activity:Activity, iconName: String):Drawable{
    when (iconName) {
        IconType.CLEAR_DAY-> return activity.getResources().getDrawable(R.drawable.clear_day, activity.getApplicationContext().getTheme())
        IconType.CLEAR_NIGHT-> return activity.getResources().getDrawable(R.drawable.clear_night, activity.getApplicationContext().getTheme())
        IconType.RAIN-> return activity.getResources().getDrawable(R.drawable.rain, activity.getApplicationContext().getTheme())
        IconType.SNOW-> return activity.getResources().getDrawable(R.drawable.snow, activity.getApplicationContext().getTheme())
        IconType.SLEET-> return activity.getResources().getDrawable(R.drawable.sleet, activity.getApplicationContext().getTheme())
        IconType.WIND-> return activity.getResources().getDrawable(R.drawable.wind, activity.getApplicationContext().getTheme())
        IconType.FOG-> return activity.getResources().getDrawable(R.drawable.fog, activity.getApplicationContext().getTheme())
        IconType.CLOUDY-> return activity.getResources().getDrawable(R.drawable.cloudy, activity.getApplicationContext().getTheme())
        IconType.PARTLY_CLOUDY_DAY-> return activity.getResources().getDrawable(R.drawable.partly_cloudy_day, activity.getApplicationContext().getTheme())
        IconType.PARTLY_CLOUDY_NIGHT-> return activity.getResources().getDrawable(R.drawable.partly_cloudy_night, activity.getApplicationContext().getTheme())
        IconType.THUNDERSTORM-> return activity.getResources().getDrawable(R.drawable.thunderstorm, activity.getApplicationContext().getTheme())
        IconType.TORNADO-> return activity.getResources().getDrawable(R.drawable.tornado, activity.getApplicationContext().getTheme())
        else -> { // Note the block
            return activity.getResources().getDrawable(R.drawable.default_weather, activity.getApplicationContext().getTheme())
        }
    }
}