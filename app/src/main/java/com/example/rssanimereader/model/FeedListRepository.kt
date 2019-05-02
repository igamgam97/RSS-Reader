package com.example.rssanimereader.model

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.feedListDataSource.FeedListDataSourceFactory
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedListRepository(
    private val netManager: NetManager,
    downloadUrlSourceManager: DownloadUrlSourceManager,
    dataBaseLoader: DataBaseLoader
) {

    private val feedListDataSourceFactory =
        FeedListDataSourceFactory(
            downloadUrlSourceManager,
            dataBaseLoader
        )


    fun getFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {
        netManager.isConnectedToInternet?.let {
            feedListDataSourceFactory(it).getFeeds(onDataReady)
        }

    }
}

