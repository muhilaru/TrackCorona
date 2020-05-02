package com.example.trackcorona

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.heatmaps.WeightedLatLng

// class used to manage data from COVID-19 data sets
class DataPoint(var name: String, var location: LatLng, var density: Double): ClusterItem  {


    fun makeWeighted(): WeightedLatLng {
        return WeightedLatLng(location, density)
    }

    override fun getPosition(): LatLng {
        return location
    }

    override fun getTitle(): String {
        return name
    }

    // used to label markers on google map
    override fun getSnippet(): String {
        return "Confirmed Cases: " + density.toInt()
    }


}
