package com.example.rssanimereader.presentation.view_model


import android.content.Intent
import android.view.MenuItem
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.domain.use_case.SetIsFavoriteFeedsUseCase
import com.example.rssanimereader.domain.use_case.SetIsReadUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class FeedViewModel(
    private val setIsFavoriteFeedsUseCase: SetIsFavoriteFeedsUseCase,
    private val setIsReadUseCase: SetIsReadUseCase
) : ViewModel() {

    lateinit var feedItem: ObservableField<FeedItem>
    val isFavorite = ObservableBoolean(false)
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
        feedItem.get()?.let {
            it.itemFavorite = !it.itemFavorite
            val disposable = setIsFavoriteFeedsUseCase(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isFavorite.set(it.itemFavorite) }, ::handleError)
            compositeDisposable.add(disposable)
        }

    }

    fun setIsReadFeed() {
        feedItem.get()?.let {
            if (!it.isRead) {
                val disposable = setIsReadUseCase(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ it.isRead = true }, ::handleError)
                compositeDisposable.add(disposable)
            }
        }

    }

    private fun shareFeed() {
        feedItem.get()?.let {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, it.itemTitle)
            shareIntent.putExtra(Intent.EXTRA_TEXT, it.itemLink)
            shareData.value = shareIntent
        }
    }

    private fun handleError(error: Throwable) {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}