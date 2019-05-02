package com.example.rssanimereader.model

import android.util.Log
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedListRepository(
    private val netManager: NetManager,
    downloadUrlSourceManager: DownloadUrlSourceManager,
    dataBaseLoader: DataBaseLoader
) {

    private val localDataSource = FeedListLocalDataSource(dataBaseLoader)
    private val remoteDataSource = FeedListRemoteDataSource(downloadUrlSourceManager, dataBaseLoader)

    fun getFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {
        // todo вернуть it
        netManager.isConnectedToInternet?.let {
            if (it) {
                remoteDataSource.saveFeeds { remoteDataSource.getFeeds { data -> onDataReady(data) } }
                //remoteDataSource.getFeeds { data -> onDataReady(data) }
            } else {
                localDataSource.getFeeds { data -> onDataReady(data) }
            }
        }
    }
}

