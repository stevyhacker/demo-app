package com.demo.app.ui.weather.future

import com.demo.app.R
import com.demo.app.data.db.entity.FutureWeatherEntry
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.view.*

class FutureWeatherItem(

    private val weatherEntry: FutureWeatherEntry

) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            containerView.textView_condition.text = weatherEntry.day.condition.text
            containerView.textView_date.text = weatherEntry.date
            containerView.textView_temperature.text = "${weatherEntry.day.avgtempC} °C"
            updateConditionImage()
        }
    }

    override fun getLayout() = R.layout.item_future_weather


    private fun ViewHolder.updateConditionImage() {
        Picasso.with(containerView.context)
            .load("https:" + weatherEntry.day.condition.icon)
            .into(containerView.imageView_condition_icon)
    }
}