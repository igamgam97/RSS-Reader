package com.example.rssanimereader.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedRepository

class FeedViewModel : ViewModel() {

    private var feedRepository = FeedRepository()

    val feedHTMLItem = MutableLiveData<String>()

    fun loadGeneratedHTML(item:FeedItem){
         feedRepository.getHTMLTFeed(item) { feedHTMLItem ->
             this.feedHTMLItem.value = feedHTMLItem
         }
    }
}