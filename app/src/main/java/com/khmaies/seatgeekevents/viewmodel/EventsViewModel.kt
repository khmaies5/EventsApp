package com.khmaies.seatgeekevents.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khmaies.seatgeekevents.model.EventsResponse
import com.khmaies.seatgeekevents.repository.EventsRepository
import com.khmaies.seatgeekevents.utils.State
import kotlinx.coroutines.launch

class EventsViewModel(private val repository: EventsRepository) : ViewModel() {

    private val _eventsListLiveData =
        MutableLiveData<State<EventsResponse>>()

    val eventsListLiveData: LiveData<State<EventsResponse>>
        get() = _eventsListLiveData

     var currentPage = 1




    fun fetchData(query: String? = null) {
        viewModelScope.launch {
            _eventsListLiveData.postValue(State.loading())
            currentPage = 1 // Reset page for new search or refresh
            val data = repository.fetchEvents(query = query, currentPage)
            _eventsListLiveData.postValue(State.success(data))
        }
    }

    fun addMoreData(query: String? = null, pageIndex: Int) {
        viewModelScope.launch {
            _eventsListLiveData.postValue(State.loading())
            val data = repository.fetchEvents(query = query, pageIndex)
            _eventsListLiveData.postValue(State.success(data))
            currentPage = pageIndex
        }
    }
}