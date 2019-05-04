package com.example.rssanimereader.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rssanimereader.model.repository.FeedListRepository
import com.example.rssanimereader.model.repository.SearchRepository
import com.example.rssanimereader.viewmodel.FeedListViewModel
import com.example.rssanimereader.viewmodel.SearchViewModel

class FeedListViewModelFactory(private val repository: FeedListRepository)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedListViewModel::class.java)) {
            return FeedListViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}


class SearchViewModelFactory(private val repository: SearchRepository)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}