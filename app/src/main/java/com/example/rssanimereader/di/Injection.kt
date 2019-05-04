package com.example.rssanimereader.di

import android.content.Context
import com.example.rssanimereader.model.FeedListRepository
import com.example.rssanimereader.model.feedListDataSource.FeedListDataSource
import com.example.rssanimereader.model.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

object Injection {

    fun provideMainViewModelFactory(context: Context): FeedListViewModelFactory {
        val dataBaseLoader = DataBaseLoader(context)
        val netManager = NetManager(context)
        val downloadUrlSourceManager = DownloadUrlSourceManager(context)
        val feedListDataSourceFactory = FeedListDataSourceFactory(
            downloadUrlSourceManager,
            dataBaseLoader
        )
        val feedListRepository = FeedListRepository(
            netManager,
            feedListDataSourceFactory
        )

        return FeedListViewModelFactory(feedListRepository)
    }
}