package com.example.rssanimereader.di

import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.rssanimereader.ProvideContextApplication
import com.example.rssanimereader.model.dataSource.ChannelListDataSource
import com.example.rssanimereader.model.dataSource.FeedDataSource
import com.example.rssanimereader.model.dataSource.LocalDS
import com.example.rssanimereader.model.dataSource.SettingsDataSource
import com.example.rssanimereader.model.dataSource.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.model.repository.ChannelsRepository
import com.example.rssanimereader.model.repository.FeedListRepository
import com.example.rssanimereader.model.repository.FeedsRepository
import com.example.rssanimereader.model.repository.SearchRepository
import com.example.rssanimereader.presentation.view.*
import com.example.rssanimereader.presentation.viewmodel.*
import com.example.rssanimereader.usecase.*
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.ChannelAPI
import com.example.rssanimereader.web.NewImageSaver
import com.example.rssanimereader.web.WebApi
import com.example.rssanimereader.web.parser.RSSRemoteDataParser

object Injection {

    private val contextApplication = ProvideContextApplication.applicationContext()
    private val dataBaseConnection = ProvideContextApplication.getDataBaseConnection()
    private lateinit var feedListViewModel: FeedListViewModel
    private lateinit var feedViewModel: FeedViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var channelListViewModel: ChannelListViewModel
    private lateinit var settingViewModel: SettingsViewModel
    // todo опрокинуть подключение к бд
    fun provideWebApi(): WebApi {
        val rssRemoteDataParser = RSSRemoteDataParser()
        val imageSaver = NewImageSaver()
        return WebApi(rssRemoteDataParser, imageSaver)
    }

    /* fun provideFeedApi(datBaseConnection: DatabaseAPI): FeedApi {
         return FeedApi(datBaseConnection)
     }*/


    fun provideFeedListViewModel(fragment: FeedListFragment) = if (!Injection::feedListViewModel.isInitialized) {
        val netManager = NetManager(contextApplication)
        val feedListDataSourceFactory =
            FeedListDataSourceFactory(
                dataBaseConnection,
                provideWebApi()
            )
        val feedListRepository = FeedListRepository(
            netManager,
            feedListDataSourceFactory
        )
        val feedListViewModelFactory = FeedListViewModelFactory(feedListRepository)
        ViewModelProviders.of(fragment, feedListViewModelFactory)
            .get(FeedListViewModel::class.java)
    } else {
        feedListViewModel
    }


    fun provideAddChannelViewModel(fragment: AddChannelDialogFragment) =
        if (!Injection::searchViewModel.isInitialized) {
            val channelSubscriptionsAPI = ChannelAPI(dataBaseConnection)
            val searchRepository = SearchRepository(channelSubscriptionsAPI)
            val webApi = provideWebApi()
            val localDS = LocalDS(dataBaseConnection)
            val channelRepository = ChannelsRepository(webApi, localDS)
            val checkIsChannelCorrectUseCase = CheckIsChannelExistUseCase(channelRepository)
            val searchViewModelFactory = SearchViewModelFactory(checkIsChannelCorrectUseCase)
            ViewModelProviders.of(fragment, searchViewModelFactory)
                .get(SearchViewModel::class.java)
        } else {
            searchViewModel
        }

    fun provideChannelListViewModel(fragment: ChannelListFragment) =
        if (!Injection::channelListViewModel.isInitialized) {
            val channelListDataSource = ChannelListDataSource(dataBaseConnection)
            val webApi = provideWebApi()
            val localDS = LocalDS(dataBaseConnection)
            val channelRepository = ChannelsRepository(webApi, localDS)
            val getChannelsUseCase = GetChannelsUseCase(channelRepository)
            val deleteChannelsUseCase = DeleteChannelsUseCase(channelRepository)
            val retractDeleteBySwipeChannelUseCase = RetractDeleteBySwipeChannelUseCase(channelRepository)
            val channelListViewModelFactory = ChannelListViewModelFactory(
                getChannelsUseCase,
                deleteChannelsUseCase,
                retractDeleteBySwipeChannelUseCase
            )
            ViewModelProviders.of(fragment, channelListViewModelFactory)
                .get(ChannelListViewModel::class.java)
        } else {
            channelListViewModel
        }

    fun provideSettingsViewModel(fragment: SettingsFragment) = if (!Injection::settingViewModel.isInitialized) {
        val context = ProvideContextApplication.applicationContext()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val settingsDataSource = SettingsDataSource(prefs)
        val settingsViewModelFactory = SettingsViewModelFactory(settingsDataSource)
        ViewModelProviders.of(fragment, settingsViewModelFactory)
            .get(SettingsViewModel::class.java)

    } else {
        settingViewModel
    }

    fun provideFeedViewModel(fragment: FeedFragment) = if (!Injection::feedViewModel.isInitialized) {
        val feedDataSource = FeedDataSource(dataBaseConnection)
        val localDS = LocalDS(dataBaseConnection)
        val webApi = provideWebApi()
        val feedsRepository = FeedsRepository(webApi, localDS)
        val setIsFavoriteFeeds = SetIsFavoriteFeedsUseCase(feedsRepository)
        val setIsRead = SetIsReadUseCase(feedsRepository)
        val feedViewModelFactory = FeedViewModelFactory(setIsFavoriteFeeds, setIsRead)
        ViewModelProviders.of(fragment, feedViewModelFactory)
            .get(FeedViewModel::class.java)
    } else {
        feedViewModel
    }

}