package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.web.WebApi
import com.example.rssanimereader.util.dbAPI.ChannelAndFeedApi

class FeedListDataSourceFactory(
    private val feedApi: ChannelAndFeedApi,
    private val webApi: WebApi
) {


    fun provideFeedListLocalDataSource() = FeedListLocalDataSource(feedApi)

    fun provideFeedListRemoteDataSource() = FeedListRemoteDataSource(feedApi,webApi)
}