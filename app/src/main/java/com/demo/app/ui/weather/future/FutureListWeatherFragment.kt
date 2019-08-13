package com.demo.app.ui.weather.future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.app.R
import com.demo.app.data.db.entity.FutureWeatherEntry
import com.demo.app.data.network.WeatherRepository
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import org.jetbrains.anko.support.v4.toast

class FutureListWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = FutureListWeatherFragment()
    }

    private val weatherRepository = WeatherRepository()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.future_forecast_title)

        loadData()
    }

    private fun loadData() {
        compositeDisposable.add(
            weatherRepository.getFutureWeatherList().doOnSuccess {
                // implement caching data in database currentWeatherDao.upsert(it.current)
                initRecyclerView(it.futureWeatherEntries.entries.toFutureWeatherItems())
            }
                .doOnError {
                    it.printStackTrace()
                    toast(getString(R.string.server_error))
                }.subscribe()
        )
    }

    private fun List<FutureWeatherEntry>.toFutureWeatherItems(): List<FutureWeatherItem> {
        return this.map {
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        group_loading.visibility = View.GONE

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? FutureWeatherItem)?.let {
                // display day weather details (it.weatherEntry.date, view)
            }
        }

    }

}
