package com.example.rssanimereader.model

import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager
import com.example.rssanimereader.util.feedUtil.FeedItem
import com.example.rssanimereader.util.NetManager

class FeedRepository(private val netManager: NetManager, downloadUrlSourceManager: DownloadUrlSourceManager) {

    val localDataSource = FeedRepoLocalDataSource()
    val remoteDataSource = FeedRepoRemoteDataSource(downloadUrlSourceManager)

    fun getFeeds(onRepositoryReadyCallback: OnRepositoryReadyCallback) {

        netManager.isConnectedToInternet?.let {
            if (it) {
                remoteDataSource.getFeeds(object : OnRepoRemoteReadyCallback {
                    override fun onRemoteDataReady(data: ArrayList<FeedItem>) {
                        remoteDataSource.saveRepositories(data)
                        onRepositoryReadyCallback.onDataReady(data)
                    }
                })
            } else {
                localDataSource.getFeeds(object : OnRepoLocalReadyCallback {
                    override fun onLocalDataReady(data: ArrayList<FeedItem>) {
                        onRepositoryReadyCallback.onDataReady(data)
                    }
                })
            }
        }
    }
}

interface OnRepositoryReadyCallback {
    fun onDataReady(data: ArrayList<FeedItem>)
}
