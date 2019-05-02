package com.example.rssanimereader.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {


    val isClickSearchButton = MutableLiveData<Boolean>()


    val targetChannel = ObservableField<String>()

    fun searchChannel() {
        if (!targetChannel.get().isNullOrEmpty()) {
            isClickSearchButton.value = !(isClickSearchButton.value ?: false)
        }
    }
}