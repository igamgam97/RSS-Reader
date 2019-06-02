package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.service.DownloadUrlSourceManager
import com.example.rssanimereader.service.RemoteDataSaver
import com.example.rssanimereader.util.dbAPI.FeedApi

class FeedListDataSourceFactory(
    private val downloadUrlSourceManager: DownloadUrlSourceManager,
    private val feedApi: FeedApi,
    private val remoteDataSaver: RemoteDataSaver
) {


    fun provideFeedListLocalDataSource() = FeedListLocalDataSource(feedApi)

    fun provideFeedListRemoteDataSource() = FeedListRemoteDataSource(downloadUrlSourceManager, feedApi,remoteDataSaver)
}