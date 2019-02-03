package com.csc.finalweather.data.repository.datasource

import com.csc.finalweather.data.cache.WeatherCache
import com.csc.finalweather.data.net.WeatherApiHttpService
import com.csc.finalweather.data.repository.datasource.local.LocalWeatherDataStore
import com.csc.finalweather.data.repository.datasource.local.LocalWeatherDataStoreImpl
import com.csc.finalweather.data.repository.datasource.remote.RemoteWeatherDataStore

class  WeatherDataStoreFactory(val weatherHttpRestApi: WeatherApiHttpService, val weatherCache: WeatherCache) {

    //get Data from Local Cached Data Store
    fun getLocalWeatherDataStore(): LocalWeatherDataStore {
        return LocalWeatherDataStoreImpl(weatherCache)
    }

    //get Data from Server API
    fun getRemoteWeatherDataStore(): RemoteWeatherDataStore {
        return RemoteWeatherDataStore(weatherHttpRestApi, weatherCache)
    }


}