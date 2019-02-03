package com.csc.finalweather.presentation.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.csc.finalweather.domain.types.UnitType
import java.text.DecimalFormat

fun Double.formatWith2digitsWithPercentage():String{
    return  DecimalFormat("0.00").format(this)+ UnitType.SPACE + UnitType.PERCENT
}

fun Double.formatKmh():String{
    return  this.toString() + UnitType.SPACE + UnitType.KMH
}

fun Double.formatCelsius():String{
    return  this.toString() + UnitType.SPACE + UnitType.CELSIUS
}

fun Long.formatWithTimezone(timezone:String):String{

    // convert seconds to milliseconds
    val date = java.util.Date(this * 1000L)
// the format of your date
    val sdf = java.text.SimpleDateFormat("EE")
// give a timezone reference for formatting (see comment at the bottom)
    sdf.timeZone = java.util.TimeZone.getTimeZone(timezone)
    val formattedDate = sdf.format(date)

    return  formattedDate
}


