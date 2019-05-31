package com.example.rssanimereader.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rssanimereader.model.dataSource.ChannelListDataSource
import com.example.rssanimereader.model.dataSource.FeedDataSource
import com.example.rssanimereader.model.dataSource.SettingsDataSource
import com.example.rssanimereader.model.repository.FeedListRepository
import com.example.rssanimereader.model.repository.SearchRepository
import com.example.rssanimereader.viewmodel.*

class FeedListViewModelFactory(private val repository: FeedListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedListViewModel::class.java)) {
            return FeedListViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}


class SearchViewModelFactory(private val repository: SearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}

class ChannelListViewModelFactory(private val dataSource: ChannelListDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChannelListViewModel::class.java)) {
            return ChannelListViewModel(dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

class SettingsViewModelFactory(private val dataSource: SettingsDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

class FeedViewModelFactory(private val dataSource: FeedDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            return FeedViewModel(dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

class AddChannelViewModelFactory(private val repository: SearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddChannelViewModelFactory::class.java)) {
            return SearchViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}