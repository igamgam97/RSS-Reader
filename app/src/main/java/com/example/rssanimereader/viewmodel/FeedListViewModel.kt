package com.example.rssanimereader.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class FeedListViewModel(private val feedListRepository: FeedListRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    var feeds = MutableLiveData<ArrayList<FeedItem>>()

    private val compositeDisposable = CompositeDisposable()


    init {
        getAllFeeds()
    }


    fun getFeedsByChannel(linkChannel: String) {
        isLoading.set(true)
        val disposable = feedListRepository.getFeedsByChannel(linkChannel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                isLoading.set(false)
                feeds.value = data
            }
        compositeDisposable.add(disposable)
    }

    fun getAllFeeds() {
        isLoading.set(true)
        val disposable = feedListRepository.getAllFeeds()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                isLoading.set(false)
                feeds.value = data
            }
        compositeDisposable.add(disposable)
        }


    fun onRefresh() {
        getAllFeeds()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

     public fun setSelectedItemPosition(selectedItemPosition:Int) {

    }



}
