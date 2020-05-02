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
        setContentView(R.layout.main_menu)

        //temporary solution to reading online dataset
        //TODO: Implement Async task to avoid running network connection on main thread
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)




        val mainButton = findViewById<Button>(R.id.mmbutton)
        mainButton.visibility = View.VISIBLE

        val mainTitle = findViewById<TextView>(R.id.maintitle)
        mainTitle.visibility = View.VISIBLE

        mainButton.setOnClickListener {
            val loadMap = Intent(this, VirusMap::class.java)
            startActivity(loadMap)
        }
    }



}