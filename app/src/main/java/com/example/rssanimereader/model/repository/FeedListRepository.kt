package com.example.rssanimereader.model.repository

import android.util.Log
import com.example.rssanimereader.model.dataSource.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.util.NetManager
import io.reactivex.Observable
import io.reactivex.Single
import java.io.IOException

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

    fun getFeedsByChannelFromWebApi(linkChannel: String, hasInternet:Boolean) = if (hasInternet){
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getFeedsByChannel(linkChannel)
    } else {
        throw IOException()
    }

    fun getFeedsByChannelFromWeb(linkChannel: String) = netManager
        .hasInternetConnection()
        .flatMap { hasInternet -> getFeedsByChannelFromWebApi(linkChannel,hasInternet) }

    fun getFeedsByChannel(linkChannel: String) = if (netManager.isConnectedToInternet) {
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getFeedsByChannel(linkChannel)
    } else {
        feedListDataSourceFactory.provideFeedListLocalDataSource().getFeedsByChannel(linkChannel)
    }

    /*fun getFeedsByChannel(linkChannel: String) = netManager.hasInternetConnection()
        .flatMap { hasInernet -> chooseRepository(linkChannel, hasInernet) }

    fun chooseRepository(linkChannel: String, hasInternet:Boolean) =if (hasInternet) {
            feedListDataSourceFactory.provideFeedListRemoteDataSource().getFeedsByChannel(linkChannel)
        } else {
            feedListDataSourceFactory.provideFeedListLocalDataSource().getFeedsByChannel(linkChannel)
        }*/


    fun getAllFeeds() = if (netManager.isConnectedToInternet) {
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getAllFeeds()
    } else {
        feedListDataSourceFactory.provideFeedListLocalDataSource().getAllFeeds()
    }
}

