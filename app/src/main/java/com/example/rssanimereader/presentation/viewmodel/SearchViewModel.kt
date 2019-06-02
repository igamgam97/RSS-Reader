package com.example.rssanimereader.presentation.viewmodel

import android.util.Log
import android.webkit.URLUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.model.repository.SearchRepository

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    val isClickSearchButton = MutableLiveData<Boolean>()
    val statusError = MutableLiveData<Boolean>().apply { value = true }
    val targetChannel = ObservableField<String>()

    fun searchChannel() {
        Log.d("bag", targetChannel.get().toString())
        targetChannel.get()?.let {
            if (URLUtil.isValidUrl(it)) {

                isClickSearchButton.value = !(isClickSearchButton.value ?: false)
            } else {
                statusError.value = false
            }
        }
    }
}