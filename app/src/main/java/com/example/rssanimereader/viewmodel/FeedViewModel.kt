package com.example.rssanimereader.viewmodel


import android.content.Intent
import android.view.MenuItem
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.dataSource.FeedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class FeedViewModel(private val feedDataSource: FeedDataSource) : ViewModel() {

    lateinit var feedItem: FeedItem
    lateinit var isFavorite: ObservableField<Boolean>
    val shareData = MutableLiveData<Intent>()
    private val compositeDisposable = CompositeDisposable()

    /* fun loadGeneratedHTML(item: FeedItem) {
         feedRepository.getHTMLTFeed(item) { feedHTMLItem ->
             this.feedHTMLItem.value = feedHTMLItem
         }
     }*/

    fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        when (menuItem?.itemId) {

            R.id.action_share -> shareFeed()
        }

        return true
    }
    //todo change Single on Completable

    fun setFavoriteFeed() {
        feedItem.itemFavorite = !feedItem.itemFavorite
        val disposable = feedDataSource.setFavorite(feedItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isFavorite.set(feedItem.itemFavorite) }, ::handleError)
        compositeDisposable.add(disposable)

    }

    private fun shareFeed() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, feedItem.itemTitle)
        shareIntent.putExtra(Intent.EXTRA_TEXT, feedItem.itemLink)
        shareData.value = shareIntent
    }

    private fun handleError(error: Throwable) {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}