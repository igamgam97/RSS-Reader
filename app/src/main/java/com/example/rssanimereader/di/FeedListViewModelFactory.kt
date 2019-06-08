package com.example.rssanimereader.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rssanimereader.data.dataSource.settingsDS.SettingsDataSource
import com.example.rssanimereader.presentation.view_model.*
import com.example.rssanimereader.domain.use_case.*

class FeedListViewModelFactory(
    private val getFeedsFromDBUseCase: GetFeedsFromDBUseCase,
    private val getFeedsFromWebUseCase: GetFeedsFromWebUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedListViewModel::class.java)) {
            return FeedListViewModel(getFeedsFromDBUseCase,getFeedsFromWebUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}




class ChannelListViewModelFactory(
    private val getChannelsFromDBUseCase: GetChannelsFromDBUseCase,
    private val deleteChannelsUseCase: DeleteChannelsUseCase,
    private val retractDeleteBySwipeChannelUseCase: RetractDeleteBySwipeChannelUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChannelListViewModel::class.java)) {
            return ChannelListViewModel(
                getChannelsFromDBUseCase,
                deleteChannelsUseCase,
                retractDeleteBySwipeChannelUseCase
            ) as T
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

class FeedViewModelFactory(
    private val setIsFavoriteFeedsUseCase: SetIsFavoriteFeedsUseCase,
    private val setIsReadUseCase: SetIsReadUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            return FeedViewModel(setIsFavoriteFeedsUseCase, setIsReadUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

class AddChannelViewModelFactory(private val isChannelExistUseCase: CheckIsChannelExistUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddChannelViewModel::class.java)) {
            return AddChannelViewModel(isChannelExistUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}