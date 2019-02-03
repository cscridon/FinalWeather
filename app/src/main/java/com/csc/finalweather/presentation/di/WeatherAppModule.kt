package com.csc.finalweather.presentation.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.csc.finalweather.BuildConfig
import com.csc.finalweather.data.cache.WeatherCache
import com.csc.finalweather.data.model.db.MyObjectBox
import com.csc.finalweather.data.net.WeatherApiHttpService
import com.csc.finalweather.data.repository.WeatherDataRepository
import com.csc.finalweather.data.repository.datasource.WeatherDataStoreFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WeatherAppModule(val context: Context) {


    @Singleton
    @Provides
    fun provideContext(): Context = context


    @Provides
    @Singleton
    fun providesSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun providesBoxStoreObjectDbCache(): BoxStore {

        // do this once, for example in your Application class
        var mBoxStore: BoxStore = MyObjectBox.builder().androidContext(context).build();
        if (BuildConfig.ENABLE_OBJECTBOX_DEBUG) {
            AndroidObjectBrowser(mBoxStore).start(context);
        }
        return mBoxStore
    }



    @Provides
    @Singleton
    fun provideWeatherCache(sharedPreferences:SharedPreferences, boxStoreCache:BoxStore): WeatherCache {
        return WeatherCache(sharedPreferences,boxStoreCache)
    }

    @Provides
    @Singleton
    fun providesWeatherDataStoreFactory( aionHttpRestApi:WeatherApiHttpService,weatherCache: WeatherCache): WeatherDataStoreFactory {
        return WeatherDataStoreFactory(
            aionHttpRestApi,
            weatherCache
        )
    }

    @Provides
    @Singleton
    fun providesWeatherDataRepo( weatherDataStoreFactory: WeatherDataStoreFactory): WeatherDataRepository {
        return WeatherDataRepository(weatherDataStoreFactory)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }



    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .build()
    }



    @Provides
    @Singleton
    internal fun provideWeatherHttpService(retrofit: Retrofit): WeatherApiHttpService {
        return retrofit.create<WeatherApiHttpService>(WeatherApiHttpService::class.java)
    }


    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        //gsonBuilder.serializeNulls()//remove this if json sv respose values are not null
        //gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }



}