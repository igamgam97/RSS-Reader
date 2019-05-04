package com.example.rssanimereader.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rssanimereader.model.FeedListRepository
import com.example.rssanimereader.viewmodel.FeedListViewModel

class FeedListViewModelFactory(private val repository: FeedListRepository)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedListViewModel::class.java)) {
            return FeedListViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}