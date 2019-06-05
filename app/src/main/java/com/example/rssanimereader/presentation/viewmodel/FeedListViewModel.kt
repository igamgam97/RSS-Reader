package com.example.rssanimereader.presentation.viewmodel


import android.util.Log
import android.view.MenuItem
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class FeedListViewModel(private val feedListRepository: FeedListRepository) : ViewModel() {
    //todo убрать lateinit var
    private val compositeDisposable = CompositeDisposable()
    val isLoading = ObservableField(false)
    val isLoadingFromCache = ObservableField(false)
    val feeds = MutableLiveData<ArrayList<FeedItem>>()
    var channelLink = ObservableField("")
    val statusError = MutableLiveData<Throwable>()
    val statusOfSort = MutableLiveData<Boolean>().apply { value = false }


    init {
        getFeedsFromCache()
    }


    fun onRefresh() {
        isLoading.set(true)
        channelLink.get()?.let { it ->
            val newFeeds = ArrayList<FeedItem>()
            val disposable = feedListRepository.getChannelsFromDB(it)
                .concatMapSingle { link -> feedListRepository.getFeedsFromWeb(link).map { link to it } }
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
                        Log.d("bag",feeds.value?.size.toString())
                    })
            compositeDisposable.add(disposable)
        }
    }

    fun getFeedsFromCache() {
        isLoadingFromCache.set(true)
        channelLink.get()?.let { channelLink ->
            val disposable = feedListRepository.getFeedsFromCashe(channelLink)
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
