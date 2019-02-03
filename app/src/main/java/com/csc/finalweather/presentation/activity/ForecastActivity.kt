package com.csc.finalweather

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.csc.finalweather.data.model.db.DailyWeatherTableDbModel
import com.csc.finalweather.data.repository.WeatherDataRepository
import com.csc.finalweather.presentation.di.WeatherApplication
import com.csc.finalweather.presentation.presenter.ForecastPresenter
import com.csc.finalweather.presentation.presenter.getDrawableByWeatherIconName
import kotlinx.android.synthetic.main.activity_forecast.*
import javax.inject.Inject
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.csc.finalweather.presentation.adapter.DailyWeatherRecyclerViewAdapter
import com.csc.finalweather.presentation.utils.checkIfTablet





class ForecastActivity : AppCompatActivity(), ForecastPresenter.SampleView, DailyWeatherRecyclerViewAdapter.ItemClickListener {


    @Inject
    lateinit var weatherDataRepo: WeatherDataRepository//provide data layer
    private var forecastPresenter: ForecastPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //use Landscape only for Tablets
        if (checkIfTablet(this)) {setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);}
        //setup layout
        setContentView(R.layout.activity_forecast)
        //inject dependecies
        (application as WeatherApplication).getAppComponent()?.inject(this)
        //intialize presenter
        forecastPresenter = ForecastPresenter(this@ForecastActivity)
        //present/load UI
        presentWeatherData()

    }


    fun presentWeatherData(){

        //setup current weather data
        val cachedCurrentWeatherModel = weatherDataRepo.getCurrentWeatherForecast()
        //setup presenter
        if (cachedCurrentWeatherModel!=null){
            forecastPresenter?.setCurrentLocationName(cachedCurrentWeatherModel.locationName)
            forecastPresenter?.setCurrentForecastSummary(cachedCurrentWeatherModel.summary)
            forecastPresenter?.setCurrentForecastTemperature(cachedCurrentWeatherModel.temperature)
            forecastPresenter?.setCurrentForecastFeelsLike(cachedCurrentWeatherModel.apparentTemperature)
            forecastPresenter?.setCurrentForecastWindspeed(cachedCurrentWeatherModel.windSpeed)
            forecastPresenter?.setCurrentForecastTemperatureIconByName(cachedCurrentWeatherModel.icon)
        }
        //setup this week weather data
        val cachedDailyListWeatherModel = weatherDataRepo.getDailyWeatherForecastList()
        //setup presenter
        if (cachedDailyListWeatherModel!=null){
            forecastPresenter?.setDailyWeatherDataList(cachedDailyListWeatherModel)
        }
    }


    override fun setDailyWeatherDataList(dailyData: ArrayList<DailyWeatherTableDbModel>) {

        val horizontalLayoutManager = GridLayoutManager(this,4); //LinearLayoutManager(this@ForecastActivity, LinearLayoutManager.HORIZONTAL, false)
        dailyWeatherRecyclerView.setLayoutManager(horizontalLayoutManager)
        val adapter = DailyWeatherRecyclerViewAdapter(this, dailyData)
        adapter.setClickListener(this)
        dailyWeatherRecyclerView.setAdapter(adapter)

    }

    override fun onItemClick(view: View, position: Int) { }

    override fun setCurrentLocationName(text: String) {
        cityNameTextView?.setText(text)
    }

    override fun setCurrentForecastSummary(text: String) {
        summaryTextView?.setText(text)
    }

    override fun setCurrentForecastTemperature(text: String) {
        currentTemperatureTextView?.setText(text)
    }

    override fun setCurrentForecastFeelsLike(text: String) {
        feelsLikeTextValueView?.setText(text)
    }

    override fun setCurrentForecastWindspeed(text: String) {
        windSpeedTextValueView?.setText(text)
    }

    override fun setCurrentForecastTemperatureIconByName(iconName: String) {
        currentWeatherIconImageView?.setImageDrawable(getDrawableByWeatherIconName(this,iconName));
    }


    //nullify presenter to prevent leaks
    override fun onDestroy() {
        super.onDestroy()
        forecastPresenter?.destroy()
    }
}
