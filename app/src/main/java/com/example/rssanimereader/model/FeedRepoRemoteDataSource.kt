package com.example.rssanimereader.model

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedRepoRemoteDataSource(
    private val downloadUrlSourceManager: DownloadUrlSourceManager,
    private val dataBaseLoader: DataBaseLoader
) {

    fun saveFeeds(onRemoteDataReady: () -> Unit) {

        downloadUrlSourceManager.getData("https://habr.com/ru/rss/all/all/") { onRemoteDataReady() }


    }

    fun getFeeds(onRemoteDataReady: (ArrayList<FeedItem>) -> Unit) {
        //todo check this part on logic
        dataBaseLoader.getData { data ->
            run {
                onRemoteDataReady(data)
                dataBaseLoader.close()
            }
        }
        //todo save repositories in DB
    }

    interface OnRepoRemoteReadyCallback {
        fun onRemoteDataReady(data: ArrayList<FeedItem>)
    }
}


