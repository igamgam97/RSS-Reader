package com.example.rssanimereader.model.repository

import android.util.Log
import com.example.rssanimereader.model.dataSource.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.util.NetManager
import java.io.IOException

class FeedListRepository(
    private val netManager: NetManager,
    private val feedListDataSourceFactory: FeedListDataSourceFactory
) : Repository {

    private fun getFeedsByChannelFromWebApi(linkChannel: String, hasInternet: Boolean) = if (hasInternet) {
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getFeedsByChannel(linkChannel)
    } else {
        throw IOException()
    }

    private fun getAllFeedsFromWebApi(hasInternet: Boolean) = if (hasInternet) {
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getAllFeeds()
    } else {
        throw IOException()
    }

    fun getFeedsFromWeb(linkChannel: String) =
        when (linkChannel) {
            "" -> netManager
                .hasInternetConnection()
                .flatMap { hasInternet -> getAllFeedsFromWebApi(hasInternet) }
            "favorite" -> feedListDataSourceFactory.provideFeedListLocalDataSource().getFavoriteFeeds()
            else -> {
                netManager
                    .hasInternetConnection()
                    .flatMap { hasInternet -> getFeedsByChannelFromWebApi(linkChannel, hasInternet) }
            }
        }

    fun getFeedsByChannel(linkChannel: String) = if (netManager.isConnectedToInternet) {
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getFeedsByChannel(linkChannel)
    } else {
        feedListDataSourceFactory.provideFeedListLocalDataSource().getFeedsByChannel(linkChannel)
    }


    fun getFeedsFromCashe(linkChannel: String) = when (linkChannel) {
        "" -> feedListDataSourceFactory.provideFeedListLocalDataSource().getAllFeeds()
        "favorite" -> feedListDataSourceFactory.provideFeedListLocalDataSource().getFavoriteFeeds()
        else -> feedListDataSourceFactory.provideFeedListLocalDataSource().getFeedsByChannel(linkChannel)
    }


    fun getAllFeeds() = if (netManager.isConnectedToInternet) {
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getAllFeeds()
    } else {
        feedListDataSourceFactory.provideFeedListLocalDataSource().getAllFeeds()
    }
}

