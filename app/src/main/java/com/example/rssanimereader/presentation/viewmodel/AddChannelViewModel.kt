package com.example.rssanimereader.presentation.viewmodel

import android.util.Log
import android.webkit.URLUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssanimereader.domain.usecase.CheckIsChannelExistUseCase

class AddChannelViewModel(private val checkIsChannelExistUseCase: CheckIsChannelExistUseCase) : ViewModel() {

    val isClickSearchButton = MutableLiveData<Boolean>()
    val statusError = MutableLiveData<Boolean>().apply { value = true }
    val targetChannel = ObservableField<String>()


    //todo добаваить проверку для каждого кейса
    fun searchChannel() {
        Log.d("bag", targetChannel.get().toString())
        targetChannel.get()?.let {
            if (!checkIsChannelExistUseCase(it)&& URLUtil.isValidUrl(it)) {
                isClickSearchButton.value = !(isClickSearchButton.value ?: false)
            } else {
                statusError.value = false
            }
        }
    }
}