package com.example.rssanimereader.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class FeedListViewModel(private val feedListRepository: FeedListRepository) : ViewModel() {
    //todo убрать lateinit var
    val isLoading = ObservableField(false)
    val feeds = MutableLiveData<ArrayList<FeedItem>>()
    private val compositeDisposable = CompositeDisposable()
    var channelLink: String = ""
    val statusError = MutableLiveData<Throwable>()

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
        isLoading.set(true)
        val disposable = feedListRepository.getFeedsFromWeb(channelLink)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                isLoading.set(false)
                feeds.value = data
            },
                { error ->
                    run {
                        isLoading.set(false)
                        statusError.value = error
                    }
                })
        compositeDisposable.add(disposable)
    }

    fun getFeedsFromCashe() {
        val disposable = feedListRepository.getFeedsFromCashe(channelLink)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                feeds.value = data
            },
                { error ->
                    run {
                        statusError.value = error
                    }
                })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}
