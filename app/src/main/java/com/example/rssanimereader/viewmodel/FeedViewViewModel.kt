package com.example.rssanimereader.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class FeedViewViewModel : ViewModel() {

    val htmlData = ObservableField<String>()
}