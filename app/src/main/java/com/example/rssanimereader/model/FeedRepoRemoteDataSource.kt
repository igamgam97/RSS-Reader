package com.example.rssanimereader.model

import android.os.Handler
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.feedUtil.onDownloadUrlSourceManagerCallback

class FeedRepoRemoteDataSource(private val downloadUrlSourceManager: DownloadUrlSourceManager) {

    fun getFeeds(onRepositoryReadyCallback: OnRepoRemoteReadyCallback) {
        val arrayList = ArrayList<FeedItem>()
        arrayList.add(
            FeedItem(
                "First",
                "Owner 1",
                "link 1",
                "Sat, 20 Apr 2019 17:55:23 GMT",
                "bl"
            )
        )
        arrayList.add(
            FeedItem(
                "Second",
                "Owner 2",
                "link 2",
                "Sat, 20 Apr 2019 17:55:23 GMT",
                "b"
            )
        )
        arrayList.add(
            FeedItem(
                "Third",
                "Owner 3",
                "link 3",
                "Sat, 20 Apr 2019 17:55:23 GMT",
                "bla"
            )
        )
        downloadUrlSourceManager.getData("https://habr.com/ru/rss/all/all/", object : onDownloadUrlSourceManagerCallback{
            override fun onDataReady(data: ArrayList<FeedItem>) {
                onRepositoryReadyCallback.onRemoteDataReady(arrayList)
            }

        })
        //Handler().postDelayed({ onRepositoryReadyCallback.onRemoteDataReady(arrayList) }, 2000)
    }

    fun saveRepositories(arrayList: ArrayList<FeedItem>) {

        //todo save repositories in DB
    }
}

interface OnRepoRemoteReadyCallback {
    fun onRemoteDataReady(data: ArrayList<FeedItem>)
}
