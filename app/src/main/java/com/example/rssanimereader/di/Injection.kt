package com.example.rssanimereader.di

import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.rssanimereader.ProvideContextApplication
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.dataSource.ChannelListDataSource
import com.example.rssanimereader.model.dataSource.FeedDataSource
import com.example.rssanimereader.model.dataSource.SettingsDataSource
import com.example.rssanimereader.model.dataSource.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.model.repository.FeedListRepository
import com.example.rssanimereader.model.repository.SearchRepository
import com.example.rssanimereader.service.DownloadUrlSourceManager
import com.example.rssanimereader.service.RemoteDataSaver
import com.example.rssanimereader.util.HTMLFeedFormatter
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.ChannelAPI
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import com.example.rssanimereader.util.dbAPI.FeedApi
import com.example.rssanimereader.util.feedUtil.parser.RSSRemoteDataParser
import com.example.rssanimereader.view.*

object Injection {


    // todo опрокинуть подключение к бд
    fun provideRemoteDataSaver(urlPath: String): RemoteDataSaver<FeedItem> {
        val context = ProvideContextApplication.applicationContext()
        val databaseAPI = DatabaseAPI(context)
        val htmlFeedFormatter = HTMLFeedFormatter()
        val rssRemoteDataParser = RSSRemoteDataParser(urlPath, htmlFeedFormatter)
        return RemoteDataSaver(rssRemoteDataParser, databaseAPI)
    }

    fun provideFeedApi(datBaseConnection: DatabaseAPI): FeedApi {
        return FeedApi(datBaseConnection)
    }

    private fun provideFeedListViewModelFactory(): FeedListViewModelFactory {

        val context = ProvideContextApplication.applicationContext()

        val dataBasConnection = ProvideContextApplication.getDataBaseConnection()

        val dataBaseLoader = provideFeedApi(dataBasConnection)

        val netManager = NetManager(context)

        val downloadUrlSourceManager = DownloadUrlSourceManager(context)

        val feedListDataSourceFactory =
            FeedListDataSourceFactory(
                downloadUrlSourceManager,
                dataBaseLoader
            )

        val feedListRepository = FeedListRepository(
            netManager,
            feedListDataSourceFactory
        )

        return FeedListViewModelFactory(feedListRepository)
    }

    private fun provideSearchViewModelFactory(): SearchViewModelFactory {


        val dataBasConnection = ProvideContextApplication.getDataBaseConnection()

        val channelSubscriptionsAPI = ChannelAPI(dataBasConnection)

        val searchRepository = SearchRepository(channelSubscriptionsAPI)

        return SearchViewModelFactory(searchRepository)
    }

    private fun provideChannelListViewModelFactory(): ChannelListViewModelFactory {

        val dataBasConnection = ProvideContextApplication.getDataBaseConnection()


        val channelApi = ChannelAPI(dataBasConnection)

        val channelListDataSource = ChannelListDataSource(channelApi)

        return ChannelListViewModelFactory(channelListDataSource)
    }

    private fun provideSettingsViewModelFactory(): SettingsViewModelFactory {

        val context = ProvideContextApplication.applicationContext()

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        val settingsDataSource = SettingsDataSource(prefs)

        return SettingsViewModelFactory(settingsDataSource)

    }

    private fun provideFeedViewModelFactory(): FeedViewModelFactory {

        val dataBaseConnection = ProvideContextApplication.getDataBaseConnection()


        val dataBaseLoader = provideFeedApi(dataBaseConnection)

        val feedDataSource = FeedDataSource(dataBaseLoader)

        return FeedViewModelFactory(feedDataSource)
    }

    fun provideViewModelFactory(fragment: Fragment) =
        when (fragment) {
            is FeedListFragment -> provideFeedListViewModelFactory()
            is SearchFragment -> provideSearchViewModelFactory()
            is ChannelListFragment -> provideChannelListViewModelFactory()
            is SettingsFragment -> provideSettingsViewModelFactory()
            is FeedFragment -> provideFeedViewModelFactory()
            else -> throw IllegalArgumentException("Unknown ViewModelFactory Class")
        }
}