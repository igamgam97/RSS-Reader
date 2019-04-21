package com.example.rssanimereader.data

import android.os.Handler

class FeedRepoRemoteDataSource {

    fun getFeeds(onRepositoryReadyCallback: OnRepoRemoteReadyCallback) {
        val arrayList = ArrayList<FeedItem>()
        arrayList.add(FeedItem("First", "Owner 1", "link 1", "Sat, 20 Apr 2019 17:55:23 GMT"))
        arrayList.add(FeedItem("Second", "Owner 2", "link 2", "Sat, 20 Apr 2019 17:55:23 GMT"))
        arrayList.add(FeedItem("Third", "Owner 3", "link 3", "Sat, 20 Apr 2019 17:55:23 GMT"))

        Handler().postDelayed({ onRepositoryReadyCallback.onRemoteDataReady(arrayList) }, 2000)
    }
}

interface OnRepoRemoteReadyCallback {
    fun onRemoteDataReady(data: ArrayList<FeedItem>)
}
