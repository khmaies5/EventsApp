package com.khmaies.seatgeekevents.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khmaies.seatgeekevents.repository.EventsRepository
import com.khmaies.seatgeekevents.viewmodel.EventsViewModel

class EventsViewModelFactory(private val repository: EventsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventsViewModel(repository) as T
    }
}