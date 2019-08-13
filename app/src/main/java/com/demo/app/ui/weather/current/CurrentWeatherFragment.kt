package com.demo.app.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.demo.app.R
import com.demo.app.data.db.entity.CurrentWeatherEntry
import com.demo.app.data.network.WeatherRepository
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.current_weather_fragment.*
import org.jetbrains.anko.support.v4.toast

class CurrentWeatherFragment : Fragment() {

    private val weatherRepository = WeatherRepository()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

    }

    private fun loadData() {
        compositeDisposable.add(
            weatherRepository.getCurrentWeather().doOnSuccess {
                // implement caching data in database currentWeatherDao.upsert(it.current)
                setupUI(it.current)
            }
                .doOnError {
                    it.printStackTrace()
                    toast(getString(R.string.server_error))
                }.subscribe()
        )
    }

    private fun setupUI(it: CurrentWeatherEntry) {
        group_loading.visibility = View.GONE
        updateDateToToday()
        updateTemperatures(it.tempC, it.feelslikeC)
        updateCondition(it.condition.text)
        updatePrecipitation(it.precipMm)
        updateWind(it.windDir, it.windKph)
        updateVisibility(it.visKm)
        updateLocation("Podgorica")

        Picasso.with(context)
            .load("https:${it.condition.icon}")
            .into(imageView_condition_icon)

    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        textView_temperature.text = "$temperature °C"
        textView_feels_like_temperature.text = "Feels like $feelsLike °C"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        textView_precipitation.text = "Preciptiation: $precipitationVolume mm"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        textView_wind.text = "Wind: $windDirection, $windSpeed kph"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        textView_visibility.text = "Visibility: $visibilityDistance km"
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
}
