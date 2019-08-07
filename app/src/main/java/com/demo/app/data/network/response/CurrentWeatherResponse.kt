package com.demo.app.data.network.response


import com.demo.app.data.db.entity.CurrentWeatherEntry
import com.demo.app.data.db.entity.Location
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val current: CurrentWeatherEntry,
    @SerializedName("location")
    val location: Location
)