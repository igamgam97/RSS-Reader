package com.example.rssanimereader.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.ActivityMainLayoutBinding
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.MainViewModel
import com.example.rssanimereader.viewmodel.State

class MainActivity : AppCompatActivity(), FragmentChangeListener {

    lateinit var binding: ActivityMainLayoutBinding
    lateinit var viewModel:CommunicateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout)
        //setContentView(R.layout.activity_main_layout)
        // при смене конфигурации создается еще раз
        viewModel = ViewModelProviders.of(this).get(CommunicateViewModel::class.java)
        viewModel.state.observe(this, Observer<State> {
            it?.let {
                Log.d("bag",it.toString())
                when(it){
                    State.FeedListFragment -> setFeedListFragment()
                    State.FeedFragment -> setFeedFragment()
                    else -> ""
                }
            }
        })

        /*
        val feedListFragment = FeedListFragment()
        feedListFragment.retainInstance = true
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frag_container, feedListFragment)
            .commit()
        */
    }

    override fun setFeedListFragment() {
        val feedListFragment = FeedListFragment()
        feedListFragment.retainInstance = true
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, feedListFragment, "FeedListFragmentTag")
            .addToBackStack(null)
            .commit()

    }

    override fun setFeedFragment() {
        val feedFragment = FeedFragment()
        feedFragment.retainInstance = true
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, feedFragment, "FeedFragmentTag")
            .addToBackStack(null)
            .commit()
    }
}


