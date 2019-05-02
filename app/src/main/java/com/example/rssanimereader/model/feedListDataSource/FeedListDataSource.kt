package com.example.rssanimereader.model.feedListDataSource

import com.example.rssanimereader.entity.FeedItem

interface FeedListDataSource {
    fun getFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit)
}