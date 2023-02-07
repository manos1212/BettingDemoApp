package com.example.bettingdemoapp.common

import android.app.Application
import com.example.bettingdemoapp.BuildConfig
//import com.example.bettingdemoapp.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BettingDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}