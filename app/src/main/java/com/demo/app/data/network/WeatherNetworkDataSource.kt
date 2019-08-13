package com.demo.app.data.network

import com.demo.app.data.ApixuWeatherService
import com.demo.app.data.network.response.CurrentWeatherResponse
import com.demo.app.data.network.response.FutureWeatherResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WeatherNetworkDataSource {

    val apiService = ApixuWeatherService()

    fun getCurrentWeather(
        location: String,
        languageCode: String
    ): Single<CurrentWeatherResponse> {
        return apiService.getCurrentWeather(location, languageCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFutureWeatherList(
        location: String,
        languageCode: String,
        days: Int
    ): Single<FutureWeatherResponse> {
        return apiService.getFutureWeather(location, days, languageCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}