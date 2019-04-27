package com.skoatpalace.weather.weather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skoatpalace.weather.R

class WeatherFragment: Fragment() {

    companion object{

        val EXTRA_CITY_NAME = "com.skoatpalace.weather.extras.EXTRA_CITY_NAME"

        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        return view
    }
}