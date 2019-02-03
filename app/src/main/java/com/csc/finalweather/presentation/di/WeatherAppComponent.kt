package com.csc.finalweather.presentation.di

import com.csc.finalweather.ForecastActivity
import com.csc.finalweather.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(  modules = [ WeatherAppModule::class ])
interface WeatherAppComponent {

    fun inject(weatherApplication: WeatherApplication)
    fun inject(splashActivity: SplashActivity)
    fun inject(forecastActivity: ForecastActivity)

}

