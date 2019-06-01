package com.example.rssanimereader.viewmodel

import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import android.widget.AdapterView
import com.example.rssanimereader.R


class FeedListViewModel(private val feedListRepository: FeedListRepository) : ViewModel() {
    //todo убрать lateinit var
    val isLoading = ObservableField(false)
    val isLoadingFromCashe = ObservableField(false)
    val feeds = MutableLiveData<ArrayList<FeedItem>>()
    private val compositeDisposable = CompositeDisposable()
    var channelLink: String = ""
    val statusError = MutableLiveData<Throwable>()
    val statusOfSort = MutableLiveData<Boolean>().apply { value = false }


            init {
                getAllFeeds()
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
        isLoadingFromCashe.set(true)
        val disposable = feedListRepository.getFeedsFromCashe(channelLink)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                feeds.value = data
                isLoadingFromCashe.set(false)
            },
                { error ->
                    run {
                        statusError.value = error
                        isLoadingFromCashe.set(false)
                    }
                })
        compositeDisposable.add(disposable)
    }


    fun  onMenuItemClick(item: MenuItem):Boolean {
        when (item.itemId){
            R.id.sort_by_older -> feeds.value?.sort()
            R.id.sort_by_earlier -> feeds.value?.sortDescending()
        }
        statusOfSort.value?.let {
            statusOfSort.value = !it
        }
        return true
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}
