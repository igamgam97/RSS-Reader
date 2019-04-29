package com.example.rssanimereader.model

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedRepoRemoteDataSource(private val downloadUrlSourceManager: DownloadUrlSourceManager) {

    fun getFeeds(onRemoteDataReady : (ArrayList<FeedItem>) ->Unit) {

        downloadUrlSourceManager.getData("https://habr.com/ru/rss/all/all/") {
                data -> onRemoteDataReady(data)}
    }

    fun saveRepositories(arrayList: ArrayList<FeedItem>) {

        //todo save repositories in DB
    }
    interface OnRepoRemoteReadyCallback {
        fun onRemoteDataReady(data: ArrayList<FeedItem>)
    }
}


