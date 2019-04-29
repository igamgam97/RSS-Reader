package com.example.rssanimereader.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.FeedRepository
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var feedRepository: FeedRepository = FeedRepository(
        NetManager(getApplication()),
        DownloadUrlSourceManager(getApplication()),
        DataBaseLoader(getApplication())
    )

    val isLoading = ObservableField(false)

    var feeds = MutableLiveData<ArrayList<FeedItem>>()

    val channel = ObservableField<String>()

    fun loadFeeds() {
        isLoading.set(true)
        feedRepository.getFeeds { data ->
            run {
                isLoading.set(false)
                feeds.value = data
            }
        }
    }
}
