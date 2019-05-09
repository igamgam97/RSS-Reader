package com.example.rssanimereader.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedListRepository


class FeedListViewModel(private val feedListRepository: FeedListRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    var feeds = MutableLiveData<ArrayList<FeedItem>>()


    init {
        getAllFeeds()
    }

    fun getFeedsByChannel(linkChannel:String){
        isLoading.set(true)
        feedListRepository.getFeedsByChannel(linkChannel) { data ->
            run {
                isLoading.set(false)
                feeds.value = data
                Log.d("bag",data.size.toString())
            }
        }
    }

    fun getAllFeeds(){
        isLoading.set(true)
        feedListRepository.getAllFeeds{ data ->
            run {
                isLoading.set(false)
                feeds.value = data
                Log.d("bag",data.size.toString())
            }
        }
    }

    fun onRefresh(){
        getAllFeeds()
    }


}
