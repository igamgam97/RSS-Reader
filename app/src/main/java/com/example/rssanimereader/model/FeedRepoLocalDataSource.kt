package com.example.rssanimereader.model

import android.os.Handler
import com.example.rssanimereader.entity.FeedItem

class FeedRepoLocalDataSource() {

    fun getFeeds(onRepositoryReadyCallback: OnRepoLocalReadyCallback) {
        val arrayList = ArrayList<FeedItem>()
        arrayList.add(
            FeedItem(
                "First",
                "Owner 1",
                "link 1",
                "Sat, 20 Apr 2019 17:55:23 GMT",
                "b"
            )
        )
        arrayList.add(
            FeedItem(
                "Second",
                "Owner 2",
                "link 2",
                "Sat, 20 Apr 2019 17:55:23 GMT",
                "d"
            )
        )
        arrayList.add(
            FeedItem(
                "Third",
                "Owner 3",
                "link 3",
                "Sat, 20 Apr 2019 17:55:23 GMT",
                "df"
            )
        )
        Handler().postDelayed({ onRepositoryReadyCallback.onLocalDataReady(arrayList) }, 2000)
    }
}

interface OnRepoLocalReadyCallback {
    fun onLocalDataReady(data: ArrayList<FeedItem>)
}
