package com.example.rssanimereader.viewmodel

import android.webkit.URLUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.model.repository.SearchRepository

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    val isClickSearchButton = MutableLiveData<Boolean>()
    val targetChannel = ObservableField<String>()

    fun searchChannel() {
        targetChannel.get()?.let {
            if (it.isNotEmpty() && URLUtil.isValidUrl(it)) {
                searchRepository.getData(it) {
                    isClickSearchButton.value = !(isClickSearchButton.value ?: false)
                }
            }
        }
    }
}