package com.example.rssanimereader.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssanimereader.FeedRecyclerViewAdapter
import com.example.rssanimereader.R
import com.example.rssanimereader.data.FeedItem
import com.example.rssanimereader.databinding.ActivityMainBinding
import com.example.rssanimereader.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), FeedRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val feedRecyclerViewAdapter = FeedRecyclerViewAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.feedRv.layoutManager = LinearLayoutManager(this)
        binding.feedRv.adapter = feedRecyclerViewAdapter
        viewModel.feeds.observe(this, Observer<ArrayList<FeedItem>> {
            it?.let {
                feedRecyclerViewAdapter.replaceData(it)
            }
        })
    }

    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
