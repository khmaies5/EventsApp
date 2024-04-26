package com.khmaies.seatgeekevents.di

import android.content.Context
import com.khmaies.seatgeekevents.network.EventsApiService
import com.khmaies.seatgeekevents.network.NetworkConnectionInterceptor
import com.khmaies.seatgeekevents.repository.EventsRepository
import com.khmaies.seatgeekevents.viewmodelfactory.EventsViewModelFactory

class AppContainer(private val applicationContext: Context) {

    val viewModelFactory: EventsViewModelFactory by lazy {
        EventsViewModelFactory(repository)
    }


    private val apiService: EventsApiService by lazy {
        EventsApiService(networkConnectionInterceptor)
    }

    private val repository: EventsRepository by lazy {
        EventsRepository(apiService)
    }

    private val networkConnectionInterceptor: NetworkConnectionInterceptor by lazy {
        NetworkConnectionInterceptor(applicationContext)
    }
}