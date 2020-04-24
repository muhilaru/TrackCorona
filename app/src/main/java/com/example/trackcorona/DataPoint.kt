package com.example.trackcorona

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.heatmaps.WeightedLatLng

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

    override fun getSnippet(): String {
        return name
    }


}
