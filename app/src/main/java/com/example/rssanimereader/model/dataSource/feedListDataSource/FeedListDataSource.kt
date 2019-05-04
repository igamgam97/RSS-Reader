package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem

interface FeedListDataSource {
    fun getFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit)
}