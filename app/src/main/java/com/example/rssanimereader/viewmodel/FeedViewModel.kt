package com.example.rssanimereader.viewmodel


import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.dataSource.FeedDataSource
import com.example.rssanimereader.model.repository.FeedRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class FeedViewModel(private val feedDataSource: FeedDataSource) : ViewModel() {

    lateinit var feedItem: FeedItem

    private var feedRepository = FeedRepository()

    val feedHTMLItem = MutableLiveData<String>()

    private val compositeDisposable = CompositeDisposable()

    fun loadGeneratedHTML(item: FeedItem) {
        feedRepository.getHTMLTFeed(item) { feedHTMLItem ->
            this.feedHTMLItem.value = feedHTMLItem
        }
    }

    fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_favorite -> setFavorite()
        }

        return true
    }

    fun setFavorite() {
        Log.d("bag", feedItem.itemFavorite.toString())
        feedItem.itemFavorite = !feedItem.itemFavorite
        val disposable = feedDataSource.setFavorite(feedItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, ::handleError)
        compositeDisposable.add(disposable)
    }

    private fun handleError(error: Throwable) {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}