package com.example.rssanimereader.viewmodel

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.view.ListOfTypeFragment
import java.util.*


class CommunicateViewModel : ViewModel() {
    val mListOfTypeFragment: MutableLiveData<Stack<ListOfTypeFragment>> = MutableLiveData()

    val listOfTypeFragment: LiveData<Stack<ListOfTypeFragment>>
        get() = mListOfTypeFragment

    init {
        val stack = Stack<ListOfTypeFragment>()
        stack.push(ListOfTypeFragment.FeedListFragment)
        mListOfTypeFragment.value = stack
    }

    fun onFeedListFramgentState() {
        val stack = mListOfTypeFragment.value
        stack!!.push(ListOfTypeFragment.FeedListFragment)
        mListOfTypeFragment.value = stack
    }

    fun onSerchFramentState() {
        val stack = mListOfTypeFragment.value
        stack!!.push(ListOfTypeFragment.SearchFragment)
        mListOfTypeFragment.value = stack
    }

    fun onFeedFramentState() {
        val stack = mListOfTypeFragment.value
        stack!!.push(ListOfTypeFragment.FeedFragment)
        mListOfTypeFragment.value = stack
    }

    fun onSettingsFramentState() {
        val stack = mListOfTypeFragment.value
        stack!!.push(ListOfTypeFragment.SettingsFragment)
        mListOfTypeFragment.value = stack
    }

    fun onChannelListFramentState() {
        val stack = mListOfTypeFragment.value
        stack!!.push(ListOfTypeFragment.ChannelListFragment)
        mListOfTypeFragment.value = stack
    }

    val targetChannel = MutableLiveData<String>()

    lateinit var selectedFeed: FeedItem

    fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_bar_channels -> onChannelListFramentState()
            R.id.app_bar_search -> onSerchFramentState()
            R.id.app_bar_settings-> onSettingsFramentState()
        }

        return true
    }
}