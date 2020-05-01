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



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("TEST")
        setContentView(R.layout.main_menu)

        val loadMap = Intent(this, VirusMap::class.java)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

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

    fun loadHeatMap() {
//         var mProvider = new HeatmapTileProvider.Builder();

    }



}