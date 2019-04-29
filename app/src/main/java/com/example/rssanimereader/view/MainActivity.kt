package com.example.rssanimereader.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rssanimereader.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_layout)

        supportFragmentManager.beginTransaction().add(
            R.id.frag_container,
            FeedListFragment()
        ).commit()

    }
}


