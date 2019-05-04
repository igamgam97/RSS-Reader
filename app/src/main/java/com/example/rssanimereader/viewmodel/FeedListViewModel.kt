package com.example.rssanimereader.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedListRepository


class FeedListViewModel(feedListRepository: FeedListRepository) : ViewModel() {

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
