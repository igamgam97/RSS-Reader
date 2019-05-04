package com.example.rssanimereader.model

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.util.NetManager

class FeedListRepository(
    private val netManager: NetManager,
    private val feedListDataSourceFactory: FeedListDataSourceFactory
) {


    fun getFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {
        netManager.isConnectedToInternet?.let {
            feedListDataSourceFactory(it).getFeeds(onDataReady)
        }

    }
}

