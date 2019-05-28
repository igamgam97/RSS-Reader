package com.example.rssanimereader.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.io.IOException


class FeedListViewModel(private val feedListRepository: FeedListRepository) : ViewModel() {

    val isLoading = ObservableField(false)
    var feeds = MutableLiveData<ArrayList<FeedItem>>()
    private val compositeDisposable = CompositeDisposable()
    var channelLink: String? = null
    val status = MutableLiveData<Boolean>().apply { value = true }

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
    // todo опракинуть toast
    // todo решить проблему с логикой принятия ссылок
    fun getFeedsByChannelFromWeb() {
        isLoading.set(true)
        channelLink = "https://habr.com/ru/rss/all/all"
        channelLink?.let {
                val disposable = feedListRepository.getFeedsByChannelFromWeb((it))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ data ->
                        isLoading.set(false)
                        feeds.value = data
                    },
                        {error -> run{
                            isLoading.set(false)
                            status.value = false
                        }})
                compositeDisposable.add(disposable)
        }
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

    public fun setSelectedItemPosition(selectedItemPosition: Int) {

    }


}
