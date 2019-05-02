package com.example.rssanimereader.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.FeedListRepository
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager


class FeedListViewModel(application: Application) : AndroidViewModel(application) {
    private var feedListRepository: FeedListRepository = FeedListRepository(
        NetManager(getApplication()),
        DownloadUrlSourceManager(getApplication()),
        DataBaseLoader(getApplication())
    )

    val isLoading = ObservableField(false)

    var feeds = MutableLiveData<ArrayList<FeedItem>>()


    init {
        isLoading.set(true)
        feedListRepository.getFeeds { data ->
            run {
                isLoading.set(false)
                feeds.value = data
            }
        }
    }


}
