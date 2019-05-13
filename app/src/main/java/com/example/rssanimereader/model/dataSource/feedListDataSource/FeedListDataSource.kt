package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem

interface FeedListDataSource {
    fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit)
    fun getFeedsByChannel(linkChannel: String, onDataReady: (ArrayList<FeedItem>) -> Unit)
}