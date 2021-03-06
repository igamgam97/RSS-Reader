package com.example.rssanimereader.presentation.view_model


import android.util.Log
import android.view.MenuItem
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.domain.use_case.GetFeedsFromDBUseCase
import com.example.rssanimereader.domain.use_case.GetFeedsFromWebUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class FeedListViewModel(
    private val getFeedsFromDBUseCase: GetFeedsFromDBUseCase,
    private val getFeedsFromWebUseCase: GetFeedsFromWebUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val isLoading = ObservableBoolean(false)
    val isLoadingFromCache = ObservableBoolean(false)
    val feeds = MutableLiveData<ArrayList<FeedItem>>()
    var channelLink = ObservableField("")
    val statusError = MutableLiveData<Throwable>()
    val statusOfSort = MutableLiveData<Boolean>().apply { value = false }


    init {
        getFeedsFromCache()
    }


    fun onRefresh() {
        isLoading.set(true)
        Log.d("bag", "on refresh")
        channelLink.get()?.let { it ->

            val newFeeds = ArrayList<FeedItem>()
            val disposable = getFeedsFromWebUseCase(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { (link, feeds) ->
                        newFeeds.addAll(feeds)
                        this.feeds.value = newFeeds

                    },
                    { error ->
                        isLoading.set(false)
                        statusError.value = error
                    }, {
                        isLoading.set(false)
                        Log.d("bag", feeds.value?.size.toString())
                    })
            compositeDisposable.add(disposable)
        }
    }

    fun getFeedsFromCache() {
        isLoadingFromCache.set(true)
        channelLink.get()?.let { channelLink ->
            val disposable = getFeedsFromDBUseCase(channelLink)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { feeds ->
                        this.feeds.value = feeds
                        isLoadingFromCache.set(false)
                    },
                    { error ->
                        statusError.value = error
                        isLoadingFromCache.set(false)

                    })
            compositeDisposable.add(disposable)
        }
    }


    fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_older -> feeds.value?.sort()
            R.id.sort_by_earlier -> feeds.value?.sortDescending()
        }
        statusOfSort.value?.let {
            statusOfSort.value = !it
        }
        return true
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}
