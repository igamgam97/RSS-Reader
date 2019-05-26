package com.example.rssanimereader.model.repository

import com.example.rssanimereader.model.dataSource.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.util.NetManager

class FeedListRepository(
    private val netManager: NetManager,
    private val feedListDataSourceFactory: FeedListDataSourceFactory
) : Repository {

/*
    fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {

        if (netManager.isConnectedToInternet) {
            feedListDataSourceFactory.provideFeedListRemoteDataSource().getAllFeeds(onDataReady)
        } else {
            feedListDataSourceFactory.provideFeedListLocalDataSource().getAllFeeds(onDataReady)
        }

    }*/

    fun getFeedsByChannel(linkChannel: String) = if (netManager.isConnectedToInternet) {
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getFeedsByChannel(linkChannel)
    } else {
        feedListDataSourceFactory.provideFeedListLocalDataSource().getFeedsByChannel(linkChannel)
    }

    fun getAllFeeds() = if (netManager.isConnectedToInternet) {
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getAllFeeds()
    } else {
        feedListDataSourceFactory.provideFeedListLocalDataSource().getAllFeeds()
    }
}

