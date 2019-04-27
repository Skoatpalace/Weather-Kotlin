package com.skoatpalace.weather.city

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.skoatpalace.weather.App
import com.skoatpalace.weather.Database
import com.skoatpalace.weather.R
import com.skoatpalace.weather.utils.toast

class CityFragment : Fragment(), CityAdapter.CityItemListener {

    interface CityFragmentListener {
        fun onCitySelected(city: City)
    }

    var listener: CityFragmentListener? = null

    private lateinit var database: Database
    private lateinit var cities: MutableList<City>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        database = App.database
        cities = mutableListOf()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_city, container, false)
        recyclerView = view.findViewById(R.id.cities_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cities = database.getAllCities()
        adapter = CityAdapter(cities, this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fragment_city, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.action_create_city -> {
                showCreateCityDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCitySelected(city: City) {
        listener?.onCitySelected(city)
    }

    override fun onCityDeleted(city: City) {
        showDeleteCityDialog(city)
    }

    private fun showCreateCityDialog() {
        val createCityFragment = CreateCityDialogFragment()
        createCityFragment.listener = object: CreateCityDialogFragment.CreateCityDialogListener {
            override fun onDialogNegativeClick() {}

            override fun onDialogPositiveClick(cityName: String) {
                saveCity(City(cityName))
            }
        }

        createCityFragment.show(fragmentManager, "CreateCityDialogFragment")
    }



    private fun showDeleteCityDialog(city: City) {
        val deleteCityFragment = DeleteCityDialogFragment.newInstance(city.name)
        deleteCityFragment.listener = object: DeleteCityDialogFragment.DeleteCityDialogListener {

            override fun onDialogPositiveClick() {

                deleteCity(city)

            }

            override fun onDialogNegativeClick() {}

        }
        deleteCityFragment.show(fragmentManager, "DeleteCityDialogFragment")
    }

    private fun saveCity(city: City) {
        if (database.createCity(city)){
            cities.add(city)
            adapter.notifyDataSetChanged()
        }else{
            context?.toast(getString(R.string.not_create_city_toast))
        }
    }

    private fun deleteCity(city: City){
        if (database.deleteCity(city)){
            cities.remove(city)
            adapter.notifyDataSetChanged()
            context?.toast(getString(R.string.city_message_info_deleted, city.name))
        } else {
            context?.toast(getString(R.string.could_not_delete_city, city.name))
        }
    }
}