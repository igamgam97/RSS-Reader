package com.example.rssanimereader.viewmodel


import android.content.Intent
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

    fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        when (menuItem?.itemId) {
            R.id.action_favorite -> {
                if (!feedItem.itemFavorite) {
                    menuItem.setIcon(R.drawable.favorite_feed_icon)
                } else {
                    menuItem.setIcon(R.drawable.no_favorite_feed_icon)
                }
                setFavorite()
            }
        }

        return true
    }
    //todo change Single on Completable
    private fun setFavorite() {
        feedItem.itemFavorite = !feedItem.itemFavorite
        Log.d("bag", feedItem.itemFavorite.toString())
        val disposable = feedDataSource.setFavorite(feedItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, ::handleError)
        compositeDisposable.add(disposable)

    }
    // todo resolve problem
    private fun shareFeed(){
        val shareIntent = Intent(android.content.Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, feedItem.itemLink)
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "bla")
    }

    private fun handleError(error: Throwable) {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}