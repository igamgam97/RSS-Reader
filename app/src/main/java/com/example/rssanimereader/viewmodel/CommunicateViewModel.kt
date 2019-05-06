package com.example.rssanimereader.viewmodel

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.R
import com.example.rssanimereader.entity.FeedItem
import java.util.*


class CommunicateViewModel : ViewModel() {
    val mEnumFragment: MutableLiveData<Stack<EnumFragment>> = MutableLiveData()

    val enumFragment: LiveData<Stack<EnumFragment>>
        get() = mEnumFragment

    init {
        val stack = Stack<EnumFragment>()
        stack.push(EnumFragment.SearchFragment)
        mEnumFragment.value = stack
    }

    fun onFeedListFramgentState() {
        val stack = mEnumFragment.value
        stack!!.push(EnumFragment.FeedListFragment)
        mEnumFragment.value = stack
    }

    fun onSerchFramentState() {
        val stack = mEnumFragment.value
        stack!!.push(EnumFragment.SearchFragment)
        mEnumFragment.value = stack
    }

    fun onFeedFramentState() {
        val stack = mEnumFragment.value
        stack!!.push(EnumFragment.FeedFragment)
        mEnumFragment.value = stack
    }

    fun onChannelListFramentState() {
        val stack = mEnumFragment.value
        stack!!.push(EnumFragment.ChannelListFragment)
        mEnumFragment.value = stack
    }

    lateinit var targetChannel: String

    lateinit var selectedFeed: FeedItem

    fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.app_bar_channels -> onChannelListFramentState()
            R.id.app_bar_search -> onSerchFramentState()
        }

        return true
    }
}