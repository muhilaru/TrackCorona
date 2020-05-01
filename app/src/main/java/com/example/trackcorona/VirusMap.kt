package com.example.trackcorona

import android.app.Activity
import android.graphics.Color
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
import android.provider.CalendarContract
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlin.text.Charsets.UTF_8
import com.example.trackcorona.MainActivity
import com.github.mikephil.charting.utils.ColorTemplate


class VirusMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var most_cases_us: Double = 0.0
    private var piechart_names = ArrayList<String>()
    private var piechart_values = ArrayList<Int>()
    private var piechart_map = HashMap<Int, String>()



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


        var url =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv"
        var reader = URL(url).openStream().bufferedReader(UTF_8)

        var line: String? = ""
        while (line != null) {
            line = reader.readLine()
            if (line == null) {
                break
            }
            val row = line.split(",")

            if (row.size > 1 && row[0] != "UID" && row[9] != "" && row[8].toDouble() != 0.0) {
                val coordinates = LatLng(row[8].toDouble(), row[9].toDouble())
                var name = row[6]

                if (row[5].isNotBlank()) {
                    name += " (" + row[5] + " County)"
                }

                val num_of_cases = row[row.size - 1].toDouble()

                if (most_cases_us < num_of_cases) {
                    most_cases_us = num_of_cases
                }


                val locationData = DataPoint(name, coordinates, row[row.size - 1].toDouble())

                mClusterManager.addItem(locationData)
                listOfDataPoints.add(locationData.makeWeighted())
            }
        }


        url =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv"
        reader = URL(url).openStream().bufferedReader(UTF_8)

        line = ""
        while (line != null) {
            line = reader.readLine()
            if (line == null) {
                break
            }
            val row = line.split(",")

            if (row[1] == "\"Korea") {
                val coordinates = LatLng(row[3].toDouble(), row[4].toDouble())
                var name = "South Korea"
                val locationData = DataPoint(name, coordinates, row[row.size - 1].toDouble())
                mClusterManager.addItem(locationData)
                piechart_map.put(row[row.size - 1].toInt(), name)
            } else if (row[0] == "\"Bonaire") {
                val coordinates = LatLng(row[3].toDouble(), row[4].toDouble())
                var name = "Netherlands (Bonaire, Sint Eustatius, and Saba"
                val locationData = DataPoint(name, coordinates, row[row.size - 1].toDouble())
                mClusterManager.addItem(locationData)
                piechart_map.put(row[row.size - 1].toInt(), name)
            } else {
                if (row.size > 1 && row[2] != "Lat" && row[2].toDouble() != 0.0) {
                    val coordinates = LatLng(row[2].toDouble(), row[3].toDouble())
                    var name = row[1]
                    if (row[0].isNotBlank()) {
                        name += " (" + row[0] + ")"
                    }
                    val locationData = DataPoint(name, coordinates, row[row.size - 1].toDouble())
                    mClusterManager.addItem(locationData)
                    piechart_map.put(row[row.size - 1].toInt(), name)

                }
            }
        }


        val mProvider = HeatmapTileProvider.Builder().weightedData(listOfDataPoints).build()
        mProvider.setMaxIntensity(most_cases_us / 2.0)
        mProvider.setRadius(50)
        val mOverlay = mMap.addTileOverlay(TileOverlayOptions().tileProvider(mProvider))

        setUpChart()


    }

    fun setUpChart() {

        val sorted = piechart_map.toSortedMap(reverseOrder())
        var counter = 0

        var pieEntries = ArrayList<PieEntry>()

        for ((key, value) in sorted) {
            //println(value + " " + key)

            if (counter == 10) {
                break
            } else {
                pieEntries.add(PieEntry(key.toFloat(), value))
            }

            counter++;
        }

        var setData = PieDataSet(pieEntries, "Top 10 Countries")
        var colors = ColorTemplate.COLORFUL_COLORS
        setData.setColors(colors.toMutableList())
        var data = PieData(setData)


        setContentView(R.layout.data_viz)
        var chart = findViewById<PieChart>(R.id.piechart)
        chart.data = data
        chart.animateY(1000)
        chart.setEntryLabelTextSize(50.toFloat())
        chart.invalidate()


    }




}
