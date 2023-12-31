package com.example.weatherapp.ui.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.DailyWeatherAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.models.WeatherInfo
import com.example.weatherapp.viewModel.HomePageViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var dailyWeatherAdapter: DailyWeatherAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[HomePageViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        fetchWeatherInfo()
        observableFunctions()
        initRecycleView()

        searchOperations()
        searchButtonHandle()
        seeDetailButtonHandle()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchWeatherInfo(city: String = "aydin") {
        CoroutineScope(Dispatchers.IO).launch {
            val job1: Deferred<Unit> = async {
                viewModel.loadWeatherInfo(city)
            }
            val job2: Deferred<Unit> = async {
                viewModel.loadForecastWeather(city)
            }
            job1.await()
            job2.await()
        }
    }

    private fun observableFunctions() {
        viewModel.getObserverWeatherInfo().observe(viewLifecycleOwner) { t ->

            if (t != null) {

                setResultsToView(t)
            }
        }
        viewModel.getObserverForecastWeather().observe(viewLifecycleOwner) { t ->

            if (t != null) {

                dailyWeatherAdapter.setList(t.list)
            }

        }


    }

    private fun setResultsToView(weatherInfo: WeatherInfo) {
        binding.city.text = weatherInfo.name.substringBefore(" ")

        binding.weatherType.text = weatherInfo.weather[0].main

        binding.weatherTemp.text = "${weatherInfo.main.temp}°"
        binding.weatherTempHeight.text = "H:${weatherInfo.main.temp_max}  "
        binding.weatherTempLow.text = "L:${weatherInfo.main.temp_min}"
        binding.humidityText.text = "${weatherInfo.main.humidity}%"
        binding.wwindText.text = "${weatherInfo.wind.speed} km/h"
        binding.cloudText.text = "${weatherInfo.clouds.all}%"
        binding.pressureText.text = ((weatherInfo.main.pressure / 1000).toDouble()).toString()
        binding.visibilityText.text = "${((weatherInfo.visibility / 1000).toDouble())} km"
        binding.feelsText.text = "${weatherInfo.main.feels_like}°"
        binding.date.text = "${convertTimestampToCustomFormat(weatherInfo.dt * 1000)}"
        val weatherIcon = binding.weatherIcon
        Picasso.get()
            .load("https://openweathermap.org/img/wn/${weatherInfo.weather[0].icon}@4x.png")
            .into(weatherIcon)
    }

    private fun initRecycleView() {
        val lmHorizontal = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = lmHorizontal
        dailyWeatherAdapter = DailyWeatherAdapter()
        binding.recyclerView.adapter = dailyWeatherAdapter
    }


    private fun searchOperations() {
        val searchView = binding.searchBar

        searchView.queryHint = "Enter a city name"
        // For example, you can set a query listener:
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                Log.d("entered", query)
                val regex = Regex("[^\\p{L}\\p{Nd}]")

                val city = query.replace(regex, "")
                fetchWeatherInfo(city)
                searchView.setQuery("", false);
                searchView.isIconified = true
                searchView.clearFocus()
                binding.city.visibility = View.VISIBLE
                binding.headerSpace.visibility = View.VISIBLE
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Handle the search query while the user is typing
                return true
            }
        })

    }

    private fun searchButtonHandle() {
        binding.searchBar.setOnSearchClickListener {
            binding.city.visibility = View.GONE
            binding.headerSpace.visibility = View.GONE
        }
        binding.searchBar.setOnCloseListener {
            binding.city.visibility = View.VISIBLE
            binding.headerSpace.visibility = View.VISIBLE
            false
        }

    }

    private fun seeDetailButtonHandle() {
        binding.seeAllBtn.setOnClickListener {

            val bundle = bundleOf("city" to binding.city.text)
            findNavController().navigate(R.id.action_homeFragment_to_dailyFragment, bundle)
        }
    }


    private fun convertTimestampToCustomFormat(timestamp: Long): String {
        try {
            // Timestamp'i Date nesnesine çevirin
            val date = Date(timestamp)

            // Tarih formatı için SimpleDateFormat nesnesi oluşturun
            val sdf = SimpleDateFormat("EEE MMM d", Locale.getDefault())

            // Tarih formatını uygulayın ve sonucu döndürün
            return sdf.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "" // Hata durumunda boş bir dize döndürün
    }
}