package com.example.rssanimereader.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rssanimereader.R

class MainActivity : AppCompatActivity(), FragmentChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_layout)

        supportFragmentManager.beginTransaction().add(
            R.id.frag_container,
            FeedListFragment()
        ).commit()

    }

    override fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(
            R.id.frag_container,
            fragment
        )
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()

    }
}


