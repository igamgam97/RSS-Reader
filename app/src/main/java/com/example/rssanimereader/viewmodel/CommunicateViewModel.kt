package com.example.rssanimereader.viewmodel

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.view.ListOfTypeFragment


class CommunicateViewModel : ViewModel() {
    val mListOfTypeFragment: MutableLiveData<ListOfTypeFragment> = MutableLiveData()

    val listOfTypeFragment: LiveData<ListOfTypeFragment>
        get() = mListOfTypeFragment

    init {
        mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragment
    }

    fun onFeedListFragmentState() {
        mListOfTypeFragment.value = ListOfTypeFragment.FeedListFragment
    }

    fun onSearchFragmentState() {
        mListOfTypeFragment.value = ListOfTypeFragment.SearchFragment
    }

    fun onFeedFragmentState() {
        mListOfTypeFragment.value = ListOfTypeFragment.FeedFragment
    }

    fun onSettingsFragmentState() {
        mListOfTypeFragment.value = ListOfTypeFragment.SettingsFragment
    }

    fun onChannelListFragmentState() {
        mListOfTypeFragment.value = ListOfTypeFragment.ChannelListFragment
    }

    val targetChannel = MutableLiveData<String>()
    val searchChannel = MutableLiveData<String>()

    lateinit var selectedFeed: FeedItem

    fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_bar_channels -> onChannelListFragmentState()
            R.id.app_bar_search -> onSearchFragmentState()
            R.id.app_bar_settings-> onSettingsFragmentState()
            R.id.app_bar_feeds -> onFeedListFragmentState()
        }

        return true
    }
}