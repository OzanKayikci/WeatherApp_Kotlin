package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.models.ForecastListItem
import com.example.weatherapp.models.WeatherForecast
import com.example.weatherapp.models.WeatherInfo
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyWeatherAdapter : RecyclerView.Adapter<DailyWeatherAdapter.MyCustomHolder>() {

    private var liveData: List<ForecastListItem>? = null

    fun setList(liveData: List<ForecastListItem>) {
        this.liveData = liveData

        notifyDataSetChanged()//From recycleView
    }

    class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tempText = view.findViewById<TextView>(R.id.cartTempText)
        private val weatherIcon = view.findViewById<ImageView>(R.id.cardWeatherIcon)
        private val date = view.findViewById<TextView>(R.id.cardDate)
        private val time = view.findViewById<TextView>(R.id.cardTime)


        private val dateFormat = SimpleDateFormat("dd-MM", Locale.getDefault())
        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        fun bind(data: ForecastListItem) {
            tempText.text = data.main.temp.toString()
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
            LayoutInflater.from(parent.context).inflate(R.layout.weather_card_item, parent, false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bind(liveData!![position])
    }

    override fun getItemCount(): Int {
        return if (liveData == null) {
            0
        } else {

          5

        }
    }


}