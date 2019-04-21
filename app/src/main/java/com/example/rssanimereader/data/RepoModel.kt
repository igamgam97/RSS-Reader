package com.example.rssanimereader.data

import android.os.Handler
import com.example.rssanimereader.util.NetManager

class FeedRepository(val netManager: NetManager) {

    val localDataSource = FeedRepoLocalDataSource()
    val remoteDataSource = FeedRepoRemoteDataSource()

    fun getFeeds(onRepositoryReadyCallback: OnRepositoryReadyCallback) {

        netManager.isConnectedToInternet?.let {
            if (it) {
                remoteDataSource.getFeeds(object : OnRepoRemoteReadyCallback {
                    override fun onRemoteDataReady(data: ArrayList<FeedItem>) {
                        localDataSource.saveRepositories(data)
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
