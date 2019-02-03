package com.csc.finalweather

import android.content.pm.ActivityInfo
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import com.anadeainc.rxbus.BusProvider
import com.anadeainc.rxbus.Subscribe
import com.csc.finalweather.data.model.server.LocationModel
import com.csc.finalweather.data.repository.WeatherDataRepository
import com.csc.finalweather.domain.events.WeatherForecastDataReadyEvent
import com.csc.finalweather.presentation.di.WeatherApplication
import com.csc.finalweather.presentation.presenter.SplashPresenter
import com.csc.finalweather.presentation.utils.*
import com.yayandroid.locationmanager.base.LocationBaseActivity
import com.yayandroid.locationmanager.configuration.Configurations
import com.yayandroid.locationmanager.configuration.LocationConfiguration
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject


class SplashActivity : LocationBaseActivity(), SplashPresenter.SampleView {

    @Inject
    lateinit var weatherDataRepo: WeatherDataRepository//provide data layer
    private var splashPresenter: SplashPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setup layout
        setContentView(R.layout.activity_splash)
        //inject dependecies
        (application as WeatherApplication).getAppComponent()?.inject(this)
        //intialize presenter
        splashPresenter = SplashPresenter(this@SplashActivity)
        //check startup flow conditions
        checkInternetAndLocation()
    }

    //check Network state & Location
    fun checkInternetAndLocation(){

        //if cache exist and not expired
        if (!weatherDataRepo.weatherDataStoreFactory.weatherCache.isCacheExpired()){
            //present Loading state
            splashPresenter?.setProgressText(progressText =  getResources().getString(R.string.splash_loading_text))
            //start next menu with cached values
            startActivityWithFadeAnimation(this@SplashActivity, ForecastActivity::class.java,true,SPLASHSCREEN_DURATION_MS)
        }else{
            //setting text feedback for the user
            splashPresenter?.setProgressText(progressText =  getResources().getString(R.string.splash_network_text))

            //if no cache or expired, check Internet connection
            if (isInternetAvailable(this@SplashActivity)){
                //setting text feedback for the user
                splashPresenter?.setProgressText(progressText = getResources().getString(R.string.splash_location_text))
                //get user location
                getLocation();
            }else{
                //Show No Internet Connection Dialog
                showNoInternetConnectionDialog(this)
            }
        }
    }
    //notify view to change UI value
    override fun setProgressText(text: String) {
        splashTextView?.setText(text)
    }

    //when current GPS location is received
    override fun onLocationChanged(receivedLocation: Location?) {
        val currentLocation =  LocationModel(latitude = receivedLocation!!.latitude , longitude = receivedLocation.longitude, locationName = reverseGeocodeCity(this,receivedLocation))
        //download Forecast for current location and cache it
        weatherDataRepo.downloadWeatherForecastForLocationAndCacheIt(currentLocation)
    }

    //show location error and finish gracefully activity
    override fun onLocationFailed(errorCode: Int) {
        showLocationError(this@SplashActivity,errorCode, getResources())
        finish()
    }

    //define GPS location permission messages
    override fun getLocationConfiguration(): LocationConfiguration {
        return Configurations.defaultConfiguration(getResources().getString(R.string.permission_dialog_title_text), getResources().getString(R.string.permission_enable_gps_text));
    }

    //listen for weather data cached event
    @Subscribe
    fun onEvent(event: WeatherForecastDataReadyEvent) {
        when (event) {
            WeatherForecastDataReadyEvent.SUCCESS -> { startActivityWithFadeAnimation(this@SplashActivity, ForecastActivity::class.java,true,0L)} //data cached so start Next Activity
            WeatherForecastDataReadyEvent.ERROR  ->  { Toast.makeText(this@SplashActivity, getResources().getString(R.string.error_title_text), Toast.LENGTH_SHORT).show();this@SplashActivity.finish() }
        }
    }


    //register Event Bus when this menu becomes visible
    override fun onResume() {
        super.onResume()
        //register event bus
        BusProvider.getInstance()?.register(this@SplashActivity);
    }

    //unregister Bus to prevent memory leaks
    override fun onPause() {
        super.onPause()
        BusProvider.getInstance()?.unregister(this@SplashActivity);
    }

    //nullify presenter to prevent leaks
    override fun onDestroy() {
        super.onDestroy()
        splashPresenter?.destroy()
    }

}
