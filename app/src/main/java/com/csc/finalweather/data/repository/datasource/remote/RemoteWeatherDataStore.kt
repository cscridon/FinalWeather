package com.csc.finalweather.data.repository.datasource.remote

import com.anadeainc.rxbus.BusProvider
import com.csc.finalweather.data.cache.WeatherCache
import com.csc.finalweather.data.model.server.LocationModel
import com.csc.finalweather.data.net.WeatherApiHttpService
import com.csc.finalweather.domain.events.WeatherForecastDataReadyEvent
import com.csc.finalweather.domain.mapper.ModelMapperHelper
import kotlinx.coroutines.experimental.launch

class  RemoteWeatherDataStore(val weatherApiHttpService: WeatherApiHttpService, val weatherCache: WeatherCache) {


      fun downloadWeatherForecastForLocationAndCacheIt(currentUserLocation: LocationModel) {

          //start a Kotlin Coroutine Async
          launch {
              try {
                  //Perform Weather data request
                  val weatherDataResponseModel = weatherApiHttpService.getWeatherForcastForLocation(currentUserLocation.latitude,currentUserLocation.longitude).execute().body()

                  //update the cache with new data
                  if (weatherDataResponseModel != null) {

                      //clean old cache before saving new data
                      weatherCache.clearDbCache()

                      //map current & weekly weather server response to corresponding db models
                      val currentWeatherDataModel = ModelMapperHelper.mapCurrentForecastResponseToDbModel(receivedServerModel = weatherDataResponseModel.currently,locationName = currentUserLocation.locationName)
                      val currentWeekListWeatherDataModel =  ModelMapperHelper.mapWeekForecastResponseToDbModel(receivedServerModel = weatherDataResponseModel.daily.data, receivedTimezone = weatherDataResponseModel.timezone)

                      //persist in db
                      weatherCache.addCurrentWeatherEntryToCache(currentWeatherDataModel)
                      weatherCache.addWeeklyWeatherListEntriesToCache(currentWeekListWeatherDataModel)

                      //when coroutine is done send OK event to update related UI states
                      BusProvider.getInstance()?.post(WeatherForecastDataReadyEvent.SUCCESS)
                      //save cache valid for offline use state
                      weatherCache.setCacheExpired(false)
                  }


              }catch (e: Exception) {
                  //send ERROR event to all listeners
                  BusProvider.getInstance()?.post(WeatherForecastDataReadyEvent.ERROR)
              }

          }
      }






}