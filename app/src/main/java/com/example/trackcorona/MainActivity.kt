package com.example.trackcorona

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.trackcorona.ui.main.SectionsPagerAdapter
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import android.os.StrictMode
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.gms.maps.model.LatLng
import com.jjoe64.graphview.helper.GraphViewXML


class MainActivity : AppCompatActivity() {

    private var piechart_map = HashMap<Int, String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_viz)



        val loadMap = Intent(this, VirusMap::class.java)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
//        loadChart()
//
        startActivity(loadMap)


//        var mainButton = findViewById<Button>(R.id.mmbutton)
//        mainButton.visibility = View.VISIBLE
//
//        var mainTitle = findViewById<TextView>(R.id.maintitle)
//        mainTitle.visibility = View.VISIBLE


//        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
//        val viewPager: ViewPager = findViewById(R.id.view_pager)
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = findViewById(R.id.tabs)
//        tabs.setupWithViewPager(viewPager)
//        val fab: FloatingActionButton = findViewById(R.id.fab)
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    fun loadChart() {

        var url =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv"
        var reader = URL(url).openStream().bufferedReader(Charsets.UTF_8)

        var line = ""
        while (line != null) {
            line = reader.readLine()
            if (line == null) {
                break
            }
            val row = line.split(",")

            if (row[1] == "\"Korea") {
                piechart_map.put(row[row.size - 1].toInt(), "South Korea")
            } else if (row[0] == "\"Bonaire") {
                piechart_map.put(
                    row[row.size - 1].toInt(),
                    "Netherlands (Bonaire, Sint Eustatius, and Saba"
                )
            } else {
                if (row.size > 1 && row[2] != "Lat" && row[2].toDouble() != 0.0) {
                    val coordinates = LatLng(row[2].toDouble(), row[3].toDouble())
                    var name = row[1]
                    if (row[0].isNotBlank()) {
                        name += " (" + row[0] + ")"
                    }
                    piechart_map.put(row[row.size - 1].toInt(), name)

                }
            }
        }

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
        var data = PieData(setData)



        var chart = findViewById<PieChart>(R.id.piechart)
//        chart.data = data
//        chart.invalidate()





    }


}