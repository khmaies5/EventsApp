package com.khmaies.seatgeekevents.repository

import com.khmaies.seatgeekevents.model.EventsResponse
import com.khmaies.seatgeekevents.network.EventsApiService
import com.khmaies.seatgeekevents.network.SafeApiRequest

class EventsRepository(private val api: EventsApiService) : SafeApiRequest() {

    suspend fun fetchEvents(query: String? = null, pageIndex: Int): EventsResponse = apiRequest {
        api.getEvents(query = query, pageIndex = pageIndex)
    }
}