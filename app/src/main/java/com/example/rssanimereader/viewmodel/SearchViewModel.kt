package com.example.rssanimereader.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.rssanimereader.model.SearchRepository

class SearchViewModel(application: Application) : AndroidViewModel(application) {


    val isClickSearchButton = MutableLiveData<Boolean>()




    val searchRepository = SearchRepository(getApplication())

    val targetChannel = ObservableField<String>()

    fun searchChannel() {

        targetChannel.get()?.let {
            if (it.isNotEmpty()){
                searchRepository.getData(it) {
                    isClickSearchButton.value = !(isClickSearchButton.value ?: false)
                }
            }
        }

    }
}