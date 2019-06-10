package com.example.rssanimereader.di

import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.rssanimereader.ProvideContextApplication
import com.example.rssanimereader.data.dataSource.localDS.LocalDS
import com.example.rssanimereader.data.dataSource.localDS.dbAPI.FeedAndChannelApi
import com.example.rssanimereader.data.dataSource.settingsDS.SettingsDataSource
import com.example.rssanimereader.data.dataSource.webDS.WebDS
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.IWebApi
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.NewImageSaver
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.RSSRemoteDataParserI
import com.example.rssanimereader.data.repository.ChannelsRepository
import com.example.rssanimereader.data.repository.FeedsRepository
import com.example.rssanimereader.domain.use_case.*
import com.example.rssanimereader.presentation.view.*
import com.example.rssanimereader.presentation.view_model.*
import com.example.rssanimereader.util.NetManager

object Injection {

    private val contextApplication = ProvideContextApplication.applicationContext()
    private lateinit var dataBaseConnection: FeedAndChannelApi
    private lateinit var feedListViewModel: FeedListViewModel
    private lateinit var feedViewModel: FeedViewModel
    private lateinit var addChannelViewModel: AddChannelViewModel
    private lateinit var channelListViewModel: ChannelListViewModel
    private lateinit var settingViewModel: SettingsViewModel

    private lateinit var webDS: WebDS
    private lateinit var localDS: LocalDS

    private lateinit var feedsRepository: FeedsRepository
    private lateinit var channelsRepository: ChannelsRepository

    //todo раскидать фабрику на несколько если будет время

    private fun provideDataBaseConnection(): FeedAndChannelApi =
        if (!Injection::dataBaseConnection.isInitialized) {
            FeedAndChannelApi(contextApplication).open()
        } else dataBaseConnection

    private fun provideWebDS(): WebDS =
        if (!Injection::webDS.isInitialized) {
            val rssRemoteDataParser = RSSRemoteDataParserI()
            val imageSaver = NewImageSaver()
            val webApi = IWebApi(rssRemoteDataParser, imageSaver)
            webDS = WebDS(webApi)
            webDS
        } else webDS

    private fun provideLocalDS(): LocalDS =
        if (!Injection::localDS.isInitialized) {
            localDS = LocalDS(provideDataBaseConnection())
            localDS
        } else localDS


    private fun provideFeedsRepository(): FeedsRepository =
        if (!Injection::feedsRepository.isInitialized) FeedsRepository(provideWebDS(), provideLocalDS())
        else feedsRepository

    private fun provideChannelsRepository(): ChannelsRepository =
        if (!Injection::feedsRepository.isInitialized) ChannelsRepository(provideWebDS(), provideLocalDS())
        else channelsRepository


    fun provideFeedListViewModel(fragment: FeedListFragment): FeedListViewModel =
        if (!Injection::feedListViewModel.isInitialized) {
            val netManager = NetManager(contextApplication)
            val getFeedsFromWebUseCase =
                GetFeedsFromWebUseCase(provideFeedsRepository(), provideChannelsRepository(), netManager)
            val getFeedsFromDBUseCase = GetFeedsFromDBUseCase(provideFeedsRepository())
            val feedListViewModelFactory = FeedListViewModelFactory(getFeedsFromDBUseCase, getFeedsFromWebUseCase)
            ViewModelProviders.of(fragment, feedListViewModelFactory)
                .get(FeedListViewModel::class.java)
        } else {
            feedListViewModel
        }


    fun provideAddChannelViewModel(fragment: AddChannelDialogFragment) =
        if (!Injection::addChannelViewModel.isInitialized) {
            val checkIsChannelCorrectUseCase = CheckIsChannelExistUseCase(provideChannelsRepository())
            val searchViewModelFactory = AddChannelViewModelFactory(checkIsChannelCorrectUseCase)
            ViewModelProviders.of(fragment, searchViewModelFactory)
                .get(AddChannelViewModel::class.java)
        } else {
            addChannelViewModel
        }

    fun provideChannelListViewModel(fragment: ChannelListFragment) =
        if (!Injection::channelListViewModel.isInitialized) {
            val getChannelsUseCase = GetChannelsFromDBUseCase(provideChannelsRepository())
            val deleteChannelsUseCase = DeleteChannelsUseCase(provideChannelsRepository())
            val retractDeleteBySwipeChannelUseCase = RetractDeleteBySwipeChannelUseCase(provideChannelsRepository())
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
        val prefs = PreferenceManager.getDefaultSharedPreferences(contextApplication)
        val settingsDataSource = SettingsDataSource(prefs)
        val settingsViewModelFactory = SettingsViewModelFactory(settingsDataSource)
        ViewModelProviders.of(fragment, settingsViewModelFactory)
            .get(SettingsViewModel::class.java)

    } else {
        settingViewModel
    }

    fun provideFeedViewModel(fragment: FeedFragment) = if (!Injection::feedViewModel.isInitialized) {
        val setIsFavoriteFeeds = SetIsFavoriteFeedsUseCase(provideFeedsRepository())
        val setIsRead = SetIsReadUseCase(provideFeedsRepository())
        val feedViewModelFactory = FeedViewModelFactory(setIsFavoriteFeeds, setIsRead)
        ViewModelProviders.of(fragment, feedViewModelFactory)
            .get(FeedViewModel::class.java)
    } else {
        feedViewModel
    }

}