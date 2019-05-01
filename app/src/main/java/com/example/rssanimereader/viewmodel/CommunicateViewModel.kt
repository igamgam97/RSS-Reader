package com.example.rssanimereader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CommunicateViewModel : ViewModel() {
    private val mState: MutableLiveData<State> = MutableLiveData<State>()

    val state: LiveData<State>
        get() = mState

    init {
        mState.value = State.FeedListFragment
    }

    fun onFeedListFramgentState() {
        mState.value = State.FeedListFragment
    }

    fun onSerchFramentState() {
        mState.value = State.SearchFragment
    }

    fun onFeedFramentState() {
        mState.value = State.FeedFragment
    }
}