package com.csc.finalweather.presentation.utils

import android.content.Context
import android.content.res.Resources
import com.yayandroid.locationmanager.constants.FailType
import android.location.Geocoder
import android.location.Location
import com.csc.finalweather.R
import java.lang.Exception
import java.util.*


fun showLocationError(context: Context, @FailType  errorCode:Int, resources: Resources){

        when (errorCode) {
            FailType.TIMEOUT -> showToastMessage(context, resources.getString(R.string.error_timeout))
            FailType.PERMISSION_DENIED -> showToastMessage(context, resources.getString(R.string.error_permission_denied))
            FailType.NETWORK_NOT_AVAILABLE -> showToastMessage(context, resources.getString(R.string.error_network_not_available))
            FailType.GOOGLE_PLAY_SERVICES_NOT_AVAILABLE -> showToastMessage(context, resources.getString(R.string.error_play_services_not_available))
            FailType.GOOGLE_PLAY_SERVICES_CONNECTION_FAIL -> showToastMessage(context, resources.getString(R.string.error_play_services_connection_failed))
            FailType.GOOGLE_PLAY_SERVICES_SETTINGS_DIALOG -> showToastMessage(context, resources.getString(R.string.error_play_services_settings_dialog))
            FailType.GOOGLE_PLAY_SERVICES_SETTINGS_DENIED -> showToastMessage(context, resources.getString(R.string.error_play_services_settings_denied))
            FailType.VIEW_DETACHED -> showToastMessage(context, resources.getString(R.string.error_view_detached))
            FailType.VIEW_NOT_REQUIRED_TYPE -> showToastMessage(context, resources.getString(R.string.error_not_required))
            FailType.UNKNOWN -> showToastMessage(context, resources.getString(R.string.error_unknow))
        }
    }

    const val DEFAULT_CITY = "Monaco"

    fun reverseGeocodeCity(context: Context,receivedLocation: Location):String{
        val gcd = Geocoder(context, Locale.getDefault())
        try{
            //code that may throw exception
            val addresses = gcd.getFromLocation(receivedLocation.latitude, receivedLocation.longitude, 1)
            var cityName = ""
            if (addresses.size > 0) {  cityName = addresses[0].locality }
            return cityName
        }catch(e: Exception){
            //code that handles exception
            return DEFAULT_CITY
        }

    }




