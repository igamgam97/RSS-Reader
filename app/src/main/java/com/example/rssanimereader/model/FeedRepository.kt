package com.example.rssanimereader.model

import android.util.Log
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedRepository(
    private val netManager: NetManager, downloadUrlSourceManager: DownloadUrlSourceManager,
    dataBaseLoader: DataBaseLoader
) {

    private val localDataSource = FeedRepoLocalDataSource(dataBaseLoader)
    private val remoteDataSource = FeedRepoRemoteDataSource(downloadUrlSourceManager, dataBaseLoader)

    fun getFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {

        netManager.isConnectedToInternet?.let {
            Log.d("bag", it.toString())
            if (it) {
                remoteDataSource.saveFeeds { remoteDataSource.getFeeds { data -> onDataReady(data) } }
                //remoteDataSource.getFeeds { data -> onDataReady(data) }
            } else {
                localDataSource.getFeeds { data -> onDataReady(data) }
            }
        }
    }
}

interface OnRepositoryReadyCallback {
    fun onDataReady(data: ArrayList<FeedItem>)
}
