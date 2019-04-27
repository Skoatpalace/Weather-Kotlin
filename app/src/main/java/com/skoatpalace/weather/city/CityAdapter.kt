package com.skoatpalace.weather.city

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.skoatpalace.weather.R

class CityAdapter(
    private val cities: List<City>,
    private val cityListener: CityAdapter.CityItemListener
) : RecyclerView.Adapter<CityAdapter.ViewHolder>(), View.OnClickListener {

    interface CityItemListener {
        fun onCitySelected(city: City)
        fun onCityDeleted(city: City)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.card_view)!!
        val cityNameView = itemView.findViewById<TextView>(R.id.name)!!
        val deleteView = itemView.findViewById<View>(R.id.delete)!!
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val viewItem = LayoutInflater.from(p0.context)
            .inflate(R.layout.item_city, p0, false)
        return ViewHolder(viewItem)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities[position]
        with(holder) {
            cardView.tag = city
            cardView.setOnClickListener(this@CityAdapter)
            cityNameView.text = city.name
            deleteView.tag = city
            deleteView.setOnClickListener(this@CityAdapter)

        }
    }

    override fun getItemCount(): Int = cities.size

    override fun onClick(view: View) {
        when (view.id) {
            R.id.card_view -> cityListener.onCitySelected(view.tag as City)
            R.id.delete -> cityListener.onCityDeleted(view.tag as City)
        }
    }

}