package com.example.rssanimereader.viewmodel


import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedRepository

class FeedViewModel : ViewModel(), Toolbar.OnMenuItemClickListener {

    private var feedRepository = FeedRepository()

    val feedHTMLItem = MutableLiveData<String>()

    fun loadGeneratedHTML(item: FeedItem) {
        feedRepository.getHTMLTFeed(item) { feedHTMLItem ->
            this.feedHTMLItem.value = feedHTMLItem
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        Log.d("bag","bag")
        when (item!!.itemId) {
            R.id.action_favorite -> Log.d("bag","favorite")
        }

        return true
    }


}