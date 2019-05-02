package com.example.rssanimereader.model.feedListDataSource

import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedListDataSourceFactory(
    private val downloadUrlSourceManager: DownloadUrlSourceManager,
    private val dataBaseLoader: DataBaseLoader
) {


    operator fun invoke(isConnected: Boolean) = when (isConnected) {
        false -> FeedListLocalDataSource(dataBaseLoader)
        true -> FeedListRemoteDataSource(
            downloadUrlSourceManager,
            dataBaseLoader
        )
    }
}