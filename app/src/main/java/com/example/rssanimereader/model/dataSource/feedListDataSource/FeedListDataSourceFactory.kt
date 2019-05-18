package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.util.dbAPI.FeedApi
import com.example.rssanimereader.service.DownloadUrlSourceManager

class FeedListDataSourceFactory(
    private val downloadUrlSourceManager: DownloadUrlSourceManager,
    private val feedApi: FeedApi
) {


    fun provideFeedListLocalDataSource() = FeedListLocalDataSource(feedApi)

    fun provideFeedListRemoteDataSource() = FeedListRemoteDataSource(downloadUrlSourceManager, feedApi)
}