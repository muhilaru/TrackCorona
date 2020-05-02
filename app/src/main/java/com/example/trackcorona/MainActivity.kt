package com.example.trackcorona

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.os.StrictMode


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        // temporary solution to reading online data set
        // TODO: Implement Async task to avoid running network connection on main thread
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        // setting up start button and title for home screen
        val mainButton = findViewById<Button>(R.id.mmbutton)
        mainButton.visibility = View.VISIBLE

        val mainTitle = findViewById<TextView>(R.id.maintitle)
        mainTitle.visibility = View.VISIBLE

        // launching google map visualization
        mainButton.setOnClickListener {
            val loadMap = Intent(this, VirusMap::class.java)
            startActivity(loadMap)
        }
    }
}