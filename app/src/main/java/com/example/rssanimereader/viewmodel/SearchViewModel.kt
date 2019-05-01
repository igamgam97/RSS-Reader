package com.example.rssanimereader.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel(){

    val isClickSearchButton = MutableLiveData<Boolean>()


    val targetChannel = ObservableField<String>()

    fun searchChannel(){
        if (!targetChannel.get().isNullOrEmpty()){
            Log.d("bag",targetChannel.get().toString())
            isClickSearchButton.value = !(isClickSearchButton.value ?: false)
        }
    }
}