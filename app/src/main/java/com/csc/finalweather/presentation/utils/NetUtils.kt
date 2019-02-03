package com.csc.finalweather.presentation.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun isInternetAvailable(context: Activity?) : Boolean {
    var isConnected = false
    if (context!=null){
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        isConnected = activeNetwork?.isConnectedOrConnecting == true
    }
    return isConnected
}


fun showNoInternetConnectionDialog(activity:Activity?){

    if (activity!=null){
        showAlertDialog(activity) {
            setTitle("Info")
            setMessage("Internet not available, check your internet connectivity and try again")
            setIcon(android.R.drawable.ic_dialog_alert)
            setCancelable(false)
            positiveButton("Retry") {
                activity.recreate()
            }
            negativeButton {
                activity.finish()
            }
        }
    }


}