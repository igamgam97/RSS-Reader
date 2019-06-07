package com.example.rssanimereader.di

import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.rssanimereader.ProvideContextApplication
import com.example.rssanimereader.model.dataSource.webDS.WebDS
import com.example.rssanimereader.model.dataSource.localDS.LocalDS
import com.example.rssanimereader.model.dataSource.settingsDS.SettingsDataSource
import com.example.rssanimereader.model.repository.ChannelsRepositoryI
import com.example.rssanimereader.model.repository.IFeedsRepository
import com.example.rssanimereader.presentation.view.*
import com.example.rssanimereader.presentation.view_model.*
import com.example.rssanimereader.domain.usecase.*
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.model.dataSource.webDS.webApi.web.NewImageSaver
import com.example.rssanimereader.model.dataSource.webDS.webApi.web.IWebApi
import com.example.rssanimereader.model.dataSource.webDS.webApi.web.RSSRemoteDataParserI

object Injection {

    private val contextApplication = ProvideContextApplication.applicationContext()
    private val dataBaseConnection = ProvideContextApplication.getDataBaseConnection()
    private lateinit var feedListViewModel: FeedListViewModel
    private lateinit var feedViewModel: FeedViewModel
    private lateinit var addChannelViewModel: AddChannelViewModel
    private lateinit var channelListViewModel: ChannelListViewModel
    private lateinit var settingViewModel: SettingsViewModel
    var webDS: WebDS? = null
    var localDS: LocalDS? = null
    // todo опрокинуть подключение к бд
    private fun provideWebApi(): IWebApi {
        val rssRemoteDataParser = RSSRemoteDataParserI()
        val imageSaver = NewImageSaver()
        return IWebApi(rssRemoteDataParser, imageSaver)
    }

    /* fun provideFeedApi(datBaseConnection: FeedAndChannelApi): FeedApi {
         return FeedApi(datBaseConnection)
     }*/

    private fun provideWebDS(): WebDS? =
        if (webDS == null) {
            val rssRemoteDataParser = RSSRemoteDataParserI()
            val imageSaver = NewImageSaver()
            val webApi = IWebApi(rssRemoteDataParser, imageSaver)
            webDS = WebDS(webApi)
            webDS
        } else webDS

    private fun provideLocalDS(): LocalDS? =
        if (localDS == null) {
            localDS = LocalDS(dataBaseConnection)
            localDS
        } else localDS


    fun provideFeedListViewModel(fragment: FeedListFragment) = if (!Injection::feedListViewModel.isInitialized) {
        val netManager = NetManager(contextApplication)
        val webApi = provideWebApi()
        val webDS = WebDS(webApi)
        val localDS = LocalDS(dataBaseConnection)
        val feedsRepository = IFeedsRepository(webDS, localDS)
        val channelsRepository = ChannelsRepositoryI(webDS, localDS)
        val getFeedsFromWebUseCase = GetFeedsFromWebUseCase(feedsRepository, channelsRepository, netManager)
        val getFeedsFromDBUseCase = GetFeedsFromDBUseCase(feedsRepository)
        val feedListViewModelFactory = FeedListViewModelFactory(getFeedsFromDBUseCase, getFeedsFromWebUseCase)
        ViewModelProviders.of(fragment, feedListViewModelFactory)
            .get(FeedListViewModel::class.java)
    } else {
        feedListViewModel
    }


    fun provideAddChannelViewModel(fragment: AddChannelDialogFragment) =
        if (!Injection::addChannelViewModel.isInitialized) {
            val webApi = provideWebApi()
            val webDS = WebDS(webApi)
            val localDS = LocalDS(dataBaseConnection)
            val channelRepository = ChannelsRepositoryI(webDS, localDS)
            val checkIsChannelCorrectUseCase = CheckIsChannelExistUseCase(channelRepository)
            val searchViewModelFactory = AddChannelViewModelFactory(checkIsChannelCorrectUseCase)
            ViewModelProviders.of(fragment, searchViewModelFactory)
                .get(AddChannelViewModel::class.java)
        } else {
            addChannelViewModel
        }

    fun provideChannelListViewModel(fragment: ChannelListFragment) =
        if (!Injection::channelListViewModel.isInitialized) {
            val webApi = provideWebApi()
            val webDS = WebDS(webApi)
            val localDS = LocalDS(dataBaseConnection)
            val channelRepository = ChannelsRepositoryI(webDS, localDS)
            val getChannelsUseCase = GetChannelsFromDBUseCase(channelRepository)
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
        val localDS = LocalDS(dataBaseConnection)
        val webApi = provideWebApi()
        val webDS = WebDS(webApi)
        val feedsRepository = IFeedsRepository(webDS, localDS)
        val setIsFavoriteFeeds = SetIsFavoriteFeedsUseCase(feedsRepository)
        val setIsRead = SetIsReadUseCase(feedsRepository)
        val feedViewModelFactory = FeedViewModelFactory(setIsFavoriteFeeds, setIsRead)
        ViewModelProviders.of(fragment, feedViewModelFactory)
            .get(FeedViewModel::class.java)
    } else {
        feedViewModel
    }

}