package com.example.rssanimereader.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.dataSource.ChannelListDataSource
import com.example.rssanimereader.model.dataSource.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.model.repository.FeedListRepository
import com.example.rssanimereader.model.repository.SearchRepository
import com.example.rssanimereader.util.HTMLFeedFormatter
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.ChannelAPI
import com.example.rssanimereader.util.dbAPI.DatabaseAPI
import com.example.rssanimereader.util.dbAPI.FeedApi
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager
import com.example.rssanimereader.util.feedUtil.RemoteDataSaver
import com.example.rssanimereader.util.feedUtil.parser.RSSRemoteDataParser
import com.example.rssanimereader.view.ChannelListFragment
import com.example.rssanimereader.view.FeedListFragment
import com.example.rssanimereader.view.SearchFragment

object Injection {

    // todo опрокинуть подключение к бд
    fun provideRemoteDataSaver(context: Context, urlPath: String): RemoteDataSaver<FeedItem> {
        val databaseAPI = DatabaseAPI(context)
        val htmlFeedFormatter = HTMLFeedFormatter()
        val rssRemoteDataParser = RSSRemoteDataParser(urlPath, htmlFeedFormatter)
        return RemoteDataSaver(urlPath, rssRemoteDataParser, databaseAPI)
    }

    fun provideDataBaseLoader(context: Context): FeedApi {
        return FeedApi(context)
    }

    private fun provideFeedListViewModelFactory(context: Context): FeedListViewModelFactory {


        val dataBaseLoader = provideDataBaseLoader(context)

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

    private fun provideSearchViewModelFactory(context: Context): SearchViewModelFactory {

        /* val dataBaseLoader=provideDataBaseLoader(context)

         val preferences=context.getSharedPreferences(FAVORITE_CHANNELS, Context.MODE_PRIVATE)*/

        val channelSubscriptionsAPI = ChannelAPI(context)

        val searchRepository = SearchRepository(channelSubscriptionsAPI)

        return SearchViewModelFactory(searchRepository)
    }

    private fun provideChannelListViewModelFactory(context: Context): ChannelListViewModelFactory {

        val channelApi = ChannelAPI(context)

        val channelListDataSource = ChannelListDataSource(channelApi)

        return ChannelListViewModelFactory(channelListDataSource)
    }

    fun provideViewModelFactory(fragment: Fragment) =
            when (fragment) {
                is FeedListFragment -> provideFeedListViewModelFactory(fragment.context!!)
                is SearchFragment -> provideSearchViewModelFactory(fragment.context!!)
                is ChannelListFragment -> provideChannelListViewModelFactory(fragment.context!!)
                else -> throw IllegalArgumentException("Unknown ViewModelFactory Class")
            }
}