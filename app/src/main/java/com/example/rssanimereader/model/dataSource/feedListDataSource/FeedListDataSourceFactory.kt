package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.data.web.WebApi
import com.example.rssanimereader.data.dbAPI.contracts.ChannelAndFeedApi

class FeedListDataSourceFactory(
    private val feedApi: ChannelAndFeedApi,
    private val webApi: WebApi
) {


    fun provideFeedListLocalDataSource() = FeedListLocalDataSource(feedApi)

    fun provideFeedListRemoteDataSource() = FeedListRemoteDataSource(feedApi,webApi)
}