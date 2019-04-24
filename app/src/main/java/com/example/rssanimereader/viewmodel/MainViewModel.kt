package com.example.rssanimereader.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager
import com.example.rssanimereader.util.feedUtil.FeedItem
import com.example.rssanimereader.model.FeedRepository
import com.example.rssanimereader.model.OnRepositoryReadyCallback
import com.example.rssanimereader.util.NetManager

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var feedRepository: FeedRepository = FeedRepository(NetManager(getApplication()),
        DownloadUrlSourceManager(getApplication())
    )

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
