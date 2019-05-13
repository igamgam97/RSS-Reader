package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.util.dbAPI.FeedApi
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedListDataSourceFactory(
        private val downloadUrlSourceManager: DownloadUrlSourceManager,
        private val feedApi: FeedApi
) {


    operator fun invoke(isConnected: Boolean) = when (isConnected) {
        false -> FeedListLocalDataSource(feedApi)
        true -> FeedListRemoteDataSource(
                downloadUrlSourceManager,
                feedApi
        )
    }
}