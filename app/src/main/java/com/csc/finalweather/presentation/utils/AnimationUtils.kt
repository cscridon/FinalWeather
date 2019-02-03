package com.csc.finalweather.presentation.utils

import android.app.Activity
import android.content.Intent
import android.os.Handler
import com.csc.finalweather.R

//animation constants
const val SPLASHSCREEN_DURATION_MS = 1200L //SplashScreen duration in ms

//Animation to fade from a menu to another
fun startActivityWithFadeAnimation(activity: Activity, cls: Class<out Activity>, shouldClose:Boolean, delayMs:Long){
    //wait DURATION
    Handler().postDelayed({
        //start activity
        activity.startActivity( Intent(activity, cls)); activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        if (shouldClose){ activity.finish() }
    }, delayMs)

}