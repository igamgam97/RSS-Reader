package com.example.rssanimereader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CommunicateViewModel : ViewModel() {
    private val mEnumFragment: MutableLiveData<EnumFragment> = MutableLiveData<EnumFragment>()

    val enumFragment: LiveData<EnumFragment>
        get() = mEnumFragment

    init {
        mEnumFragment.value = EnumFragment.SearchFragment
    }

    fun onFeedListFramgentState() {
        mEnumFragment.value = EnumFragment.FeedListFragment
    }

    fun onSerchFramentState() {
        mEnumFragment.value = EnumFragment.SearchFragment
    }

    fun onFeedFramentState() {
        mEnumFragment.value = EnumFragment.FeedFragment
    }

    var targetChannel = ""
}