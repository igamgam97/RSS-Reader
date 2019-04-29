package com.example.rssanimereader.model

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.NetManager
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedRepository(private val netManager: NetManager, downloadUrlSourceManager: DownloadUrlSourceManager) {

    private val localDataSource = FeedRepoLocalDataSource()
    private val remoteDataSource = FeedRepoRemoteDataSource(downloadUrlSourceManager)

    fun getFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {

        netManager.isConnectedToInternet?.let {
            if (it) {
                remoteDataSource.getFeeds { data -> onDataReady(data) }
            } else {
                localDataSource.getFeeds { data -> onDataReady(data) }
            }
        }
    }
}

interface OnRepositoryReadyCallback {
    fun onDataReady(data: ArrayList<FeedItem>)
}
