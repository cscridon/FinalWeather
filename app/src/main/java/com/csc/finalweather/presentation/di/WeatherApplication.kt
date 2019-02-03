package com.csc.finalweather.presentation.di

import android.app.Application


class WeatherApplication : Application() {

    private var appComponent: WeatherAppComponent? = null

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerWeatherAppComponent
            .builder()
            .weatherAppModule(WeatherAppModule(applicationContext))
            .build()

        //inject dependecies
        getAppComponent()?.inject(this)

    }


    fun getAppComponent(): WeatherAppComponent? {
        return appComponent
    }

}