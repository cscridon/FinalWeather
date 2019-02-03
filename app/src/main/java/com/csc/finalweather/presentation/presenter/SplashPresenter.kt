package com.csc.finalweather.presentation.presenter

class SplashPresenter(private var splashView: SampleView?) {

    fun setProgressText(progressText:String) {
        splashView?.setProgressText(progressText)
    }

    fun destroy() {
        splashView = null
    }

    interface SampleView {
        fun setProgressText(text: String)
    }
}