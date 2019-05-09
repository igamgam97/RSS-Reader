package com.example.rssanimereader.model.repository

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.dataSource.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.util.NetManager

class FeedListRepository(
    private val netManager: NetManager,
    private val feedListDataSourceFactory: FeedListDataSourceFactory
) : Repository {


    fun getFeedsByChannel(linkChannel:String, onDataReady: (ArrayList<FeedItem>) -> Unit) {
        netManager.isConnectedToInternet?.let {
            feedListDataSourceFactory(it).getFeedsByChannel(linkChannel, onDataReady)
        }

    }

    fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit){
        feedListDataSourceFactory(false).getAllFeeds(onDataReady)
    }
}

