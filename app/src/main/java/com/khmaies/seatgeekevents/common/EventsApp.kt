package com.khmaies.seatgeekevents.common

import android.app.Application
import com.khmaies.seatgeekevents.di.AppContainer

class EventsApp : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()

        appContainer = AppContainer(this)
    }
}