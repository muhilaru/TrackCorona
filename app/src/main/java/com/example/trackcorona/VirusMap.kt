package com.example.trackcorona

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import java.net.URL
import java.nio.charset.Charset
import java.io.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlin.text.Charsets.UTF_8


class VirusMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var most_cases_us: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_virus_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        println("WORKING")
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var listOfDataPoints = mutableListOf<WeightedLatLng>()

        var mClusterManager = ClusterManager<DataPoint>(this, mMap)
        mClusterManager.setAlgorithm(NonHierarchicalDistanceBasedAlgorithm<DataPoint>())

        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)


        val url_us =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv"
        var read_us = URL(url_us).openStream().bufferedReader(UTF_8)

        var line: String? = ""
        while (line != null) {
            line = read_us.readLine()
            if (line == null) {
                break
            }
            val row = line.split(",")

            if (row.size > 1 && row[0] != "UID" && row[9] != "" && row[8].toDouble() != 0.0) {
                val coordinates = LatLng(row[8].toDouble(), row[9].toDouble())
                var name = row[6]

                if (row[5] != "") {
                    name += " (" + row[5] + " County)"
                }

                val locationData = DataPoint(name, coordinates, row[102].toDouble())

                mClusterManager.addItem(locationData)
                listOfDataPoints.add(locationData.makeWeighted())
            }
        }


        val url =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv"
        var iterate = URL(url).openStream().bufferedReader(UTF_8)

        line = ""
        while (line != null) {
            line = iterate.readLine()
            if (line == null) {
                break
            }
            val row = line.split(",")

            var csv_error = 0
            if (row[1] == "\"Korea") {
                val coordinates = LatLng(row[3].toDouble(), row[4].toDouble())
                var name = "South Korea"
                val locationData = DataPoint(name, coordinates, row[row.size - 1].toDouble())
                mClusterManager.addItem(locationData)
            } else if (row[0] == "\"Bonaire") {
                val coordinates = LatLng(row[3].toDouble(), row[4].toDouble())
                var name = "Netherlands (Bonaire, Sint Eustatius, and Saba"
                val locationData = DataPoint(name, coordinates, row[row.size - 1].toDouble())
                mClusterManager.addItem(locationData)
            } else {
                if (row.size > 1 && row[2] != "Lat" && row[2].toDouble() != 0.0) {
                    val coordinates = LatLng(row[2].toDouble(), row[3].toDouble())
                    var name = row[1]
                    if (row[0].isNotBlank()) {
                        name += " (" + row[0] + ")"
                    }
                    val locationData = DataPoint(name, coordinates, row[row.size - 1].toDouble())
                    mClusterManager.addItem(locationData)
                }
            }
        }


        var mProvider = HeatmapTileProvider.Builder().weightedData(listOfDataPoints).build()
//        mProvider.setMaxIntensity(10000.00)
        mProvider.setRadius(50)
        val mOverlay = mMap.addTileOverlay(TileOverlayOptions().tileProvider(mProvider))


    }

}
