package com.csc.finalweather.data.cache

import android.content.SharedPreferences
import com.csc.finalweather.data.model.db.CurrentWeatherTableDbModel
import com.csc.finalweather.data.model.db.DailyWeatherTableDbModel
import io.objectbox.BoxStore


const val  CACHE_EXPIRED = "CACHE_EXPIRED"

class WeatherCache(var sharedPreferences: SharedPreferences, var boxStoreCache: BoxStore) {


    /////////////////////////////////////
    //Shared Preferences cache methods
    ////////////////////////////////////

    fun isCacheExpired(): Boolean {
        return sharedPreferences.getBoolean(CACHE_EXPIRED, true)
    }

    fun setCacheExpired(cacheExpired: Boolean) {
        //cache new user role
        sharedPreferences.edit().putBoolean(CACHE_EXPIRED, cacheExpired).apply()
    }


    //clears SharedPrefs cache
    fun clearSharedPreferencesCache() {
        sharedPreferences.edit().clear().commit()
    }


    /////////////////////////////////////
    //ObjectBOX  NO-SQL db cache methods
    ////////////////////////////////////

    //caches Current Weather data to DB
    fun addCurrentWeatherEntryToCache(currentWeatherTableDbModel: CurrentWeatherTableDbModel ){

        // Get the box of CurrentWeather (i.e., Box<CurrentWeatherTableDbModel> object) from the BoxStore
        val currentWeatherEntriesCacheBox = boxStoreCache.boxFor( CurrentWeatherTableDbModel::class.java)
        //save to db the new CurrentWeatherTableDbModel
        currentWeatherEntriesCacheBox.put(currentWeatherTableDbModel)
    }

    fun getCurrentWeatherForecast(): CurrentWeatherTableDbModel?{
        // Get the box of CurrentWeather (i.e., Box<CurrentWeatherTableDbModel> object) from the BoxStore
        val currentWeatherEntriesCacheBox = boxStoreCache.boxFor( CurrentWeatherTableDbModel::class.java)
        return currentWeatherEntriesCacheBox.query().build().findFirst();
    }

    //caches Weekly Weather data to DB
    fun addWeeklyWeatherListEntriesToCache(currentWeatherTableDbModel: ArrayList<DailyWeatherTableDbModel> ){

        // Get the box of CurrentWeather (i.e., Box<CurrentWeatherTableDbModel> object) from the BoxStore
        val dailyWeatherEntriesCacheBox = boxStoreCache.boxFor( DailyWeatherTableDbModel::class.java)
        //save to db the new CurrentWeatherTableDbModel
        dailyWeatherEntriesCacheBox.put(currentWeatherTableDbModel)
    }

    fun getWeeklyWeatherForecast(): ArrayList<DailyWeatherTableDbModel>?{
        // Get the box of CurrentWeather (i.e., Box<CurrentWeatherTableDbModel> object) from the BoxStore
        val dailyWeatherEntriesCacheBox = boxStoreCache.boxFor( DailyWeatherTableDbModel::class.java)
        return ArrayList(dailyWeatherEntriesCacheBox.query().build().find());
    }

    //clears db cache only
    fun clearDbCache() {
        //boxStoreCache?.boxFor( ExchangeCoinEntryDbModel::class.java)?.removeAll() - we want to keep coin exchange rates between logout not user related
        boxStoreCache.boxFor( CurrentWeatherTableDbModel::class.java).removeAll()
        boxStoreCache.boxFor( DailyWeatherTableDbModel::class.java).removeAll()
    }


    //clears all app cache ( SharedPrefs + DbCache)
    fun clearWeatherAppCache(){
        clearDbCache()
        clearSharedPreferencesCache()
    }
}