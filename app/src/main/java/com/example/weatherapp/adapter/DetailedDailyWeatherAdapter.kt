package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.models.ForecastListItem
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailedDailyWeatherAdapter : RecyclerView.Adapter<DetailedDailyWeatherAdapter.MyCustomHolder>() {
    private var liveData: List<ForecastListItem>? = null

    fun setList(liveData: List<ForecastListItem>) {
        this.liveData = liveData

        notifyDataSetChanged()//From recycleView
    }

    class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val weatherTemp = view.findViewById<TextView>(R.id.weatherTempD)
        private val weatherIcon = view.findViewById<ImageView>(R.id.weatherIconD)
        private val date = view.findViewById<TextView>(R.id.dateD)
        private val time = view.findViewById<TextView>(R.id.timeD)
        private val weatherType = view.findViewById<TextView>(R.id.weatherTypeD)
        private val humidity = view.findViewById<TextView>(R.id.humidityTextD)
        private val wind = view.findViewById<TextView>(R.id.windTextD)
        private val cloud = view.findViewById<TextView>(R.id.cloudTextD)
        private val pressure = view.findViewById<TextView>(R.id.pressureTextD)
        private val visibility = view.findViewById<TextView>(R.id.visibilityTextD)
        private val feels = view.findViewById<TextView>(R.id.feelsTextD)
        private val weatherTempHeight = view.findViewById<TextView>(R.id.weatherTempHeightD)
        private val weatherTempLow = view.findViewById<TextView>(R.id.weatherTempLowD)

        private val dateFormat = SimpleDateFormat("EEE MMM d", Locale.getDefault())
        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun bind(data: ForecastListItem) {

            weatherType.text = data.weather[0].main
            weatherTemp.text = "${data.main.temp}°"
            weatherTempHeight.text = "H:${data.main.temp_max}  "
            weatherTempLow.text = "L:${data.main.temp_min}"
            humidity.text = "${data.main.humidity}%"
            wind.text = "${data.wind.speed} km/h"
            cloud.text = "${data.clouds.all}%"
            pressure.text = ((data.main.pressure / 1000).toDouble()).toString()
            visibility.text = "${((data.visibility / 1000).toDouble())} km"
           feels.text = "${data.main.feels_like}°"


            date.text = dateFormat.format(Date(data.dt * 1000))
            time.text = timeFormat.format(Date(data.dt * 1000))
            Picasso.get().load("https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png")
                .into(weatherIcon)


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCustomHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.weather_detailed_card_item, parent, false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bind(liveData!![position])
    }

    override fun getItemCount(): Int {
        return if (liveData == null) {
            0
        } else {

            liveData!!.size

        }
    }
}