package com.example.rssanimereader.model.repository

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.dataSource.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.util.NetManager
import io.reactivex.Observable
import io.reactivex.Single
import java.io.IOException

class FeedListRepository(
    private val netManager: NetManager,
    private val feedListDataSourceFactory: FeedListDataSourceFactory
) : Repository {


    private fun getFeedsByChannelFromWebApi(linkChannel: String, hasInternet: Boolean) = if (hasInternet) {
        feedListDataSourceFactory.provideFeedListRemoteDataSource().getFeedsByChannelFromWeb(linkChannel)
    } else {
        throw IOException()
    }


    /* fun getFeedsFromWeb(linkChannel: String) =
         when (linkChannel) {
             "" -> netManager
                 .hasInternetConnection()
                 .flatMap { hasInternet -> getAllFeedsFromWebApi(hasInternet) }
             else -> {
                 netManager
                     .hasInternetConnection()
                     .flatMap { hasInternet -> getFeedsByChannelFromWebApi(linkChannel, hasInternet) }
             }
         }*/


    fun getFeedsFromCashe(linkChannel: String) = when (linkChannel) {
        "" -> feedListDataSourceFactory.provideFeedListLocalDataSource().getAllFeeds()
        "favorite" -> feedListDataSourceFactory.provideFeedListLocalDataSource().getFavoriteFeeds()
        else -> feedListDataSourceFactory.provideFeedListLocalDataSource().getFeedsByChannel(linkChannel)
    }

    fun getChannelsFromDB(linkChannel: String): Observable<String> =
        when (linkChannel) {
            "" -> feedListDataSourceFactory.provideFeedListRemoteDataSource().getAllChannels()
            else -> Observable.fromIterable(arrayListOf(linkChannel))
        }


    fun getFeedsFromWeb(linkChannel: String): Single<ArrayList<FeedItem>> = netManager
        .hasInternetConnection()
        .flatMap { hasInternet -> getFeedsByChannelFromWebApi(linkChannel, hasInternet) }
        .flatMap { data ->
            feedListDataSourceFactory.provideFeedListRemoteDataSource().saveFeedsByChannel(data)
                .andThen(feedListDataSourceFactory.provideFeedListRemoteDataSource().getFeedsByChannelFromDB(data.second.linkChannel))
        }

}

