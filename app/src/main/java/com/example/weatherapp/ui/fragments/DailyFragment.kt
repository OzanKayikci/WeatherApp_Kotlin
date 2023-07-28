package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapter.DetailedDailyWeatherAdapter
import com.example.weatherapp.databinding.FragmentDailyWeatherBinding
import com.example.weatherapp.viewModel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyFragment : Fragment() {
    private var _binding: FragmentDailyWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var weatherAdapter: DetailedDailyWeatherAdapter
    private lateinit var city: String

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory)[HomePageViewModel::class.java]
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyWeatherBinding.inflate(inflater, container, false)
        initRecycleView()
        city = arguments?.getString("city")!!

        fetchWeatherInfo()
        observableFunctions()

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

        viewModel.getObserverForecastWeather().observe(viewLifecycleOwner) { t ->

            if (t != null) {

                weatherAdapter.setList(t.list)
            }

        }


    }

    private fun initRecycleView() {
        val lmHorizontal = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = lmHorizontal
        weatherAdapter = DetailedDailyWeatherAdapter()
        binding.recyclerView.adapter = weatherAdapter
    }

}