package com.example.rssanimereader.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.rssanimereader.data.FeedItem
import com.example.rssanimereader.data.FeedRepository
import com.example.rssanimereader.data.OnRepositoryReadyCallback
import com.example.rssanimereader.util.NetManager

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var feedRepository: FeedRepository = FeedRepository(NetManager(getApplication()))

    val isLoading = ObservableField(false)

    var feeds = MutableLiveData<ArrayList<FeedItem>>()


    fun loadFeeds() {
        isLoading.set(true)
        feedRepository.getFeeds(object : OnRepositoryReadyCallback {
            override fun onDataReady(data: ArrayList<FeedItem>) {
                isLoading.set(false)
                feeds.value = data
            }
        })
    }
}