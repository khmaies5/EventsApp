package com.khmaies.seatgeekevents.network

import com.khmaies.seatgeekevents.BuildConfig
import com.khmaies.seatgeekevents.model.EventsResponse
import com.khmaies.seatgeekevents.utils.AppUtils.ENDPOINT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface EventsApiService {

    @GET("events")
    suspend fun getEvents(@Query("client_id") appId: String = BuildConfig.seatgeek_app_id,
                          @Query("q") query: String? = null,
                          @Query("page") pageIndex: Int) : Response<EventsResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): EventsApiService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val serverUrl = ENDPOINT
            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(logging)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EventsApiService::class.java)
        }
    }

}