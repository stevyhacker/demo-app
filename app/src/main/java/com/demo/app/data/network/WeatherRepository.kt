package com.demo.app.data.network


import com.demo.app.data.network.response.CurrentWeatherResponse
import com.demo.app.data.network.response.FutureWeatherResponse
import io.reactivex.Single

//hardcoded for demo testing purposes
const val FORECAST_DAYS_COUNT = 7
const val LANG = "en"
const val LOCATION = "Podgorica"


class WeatherRepository {

    val weatherNetworkDataSource = WeatherNetworkDataSource()

    fun getCurrentWeather(): Single<CurrentWeatherResponse> {
        return weatherNetworkDataSource.getCurrentWeather(LOCATION, LANG)
    }

    fun getFutureWeatherList(): Single<FutureWeatherResponse> {
        return weatherNetworkDataSource.getFutureWeatherList(LOCATION, LANG, FORECAST_DAYS_COUNT)
    }

//    fun getFutureWeatherByDate(date): LiveData<CurrentWeatherEntry>

}
