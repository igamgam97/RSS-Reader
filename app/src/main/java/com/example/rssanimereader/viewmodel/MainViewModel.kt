package com.example.rssanimereader.viewmodel

import android.app.Application
import android.content.ClipData
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.FeedRepository
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var feedRepository: FeedRepository = FeedRepository(
        NetManager(getApplication()),
        DownloadUrlSourceManager(getApplication()),
        DataBaseLoader(getApplication())
    )
    private val selected = MutableLiveData<FeedItem>()

    val isLoading = ObservableField(false)

    var feeds = MutableLiveData<ArrayList<FeedItem>>()

    val channel = ObservableField<String>()

    init {
        isLoading.set(true)
        feedRepository.getFeeds { data ->
            run {
                isLoading.set(false)
                feeds.value = data
            }
        }
    }


    fun select(item: FeedItem) {
        selected.value = item
    }

    fun getSelected(): LiveData<FeedItem> {
        return selected
    }

}
