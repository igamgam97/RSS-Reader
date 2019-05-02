package com.example.rssanimereader.model

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.DataBaseLoader

class FeedListLocalDataSource(private val dataBaseLoader: DataBaseLoader) {

    fun getFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {
        dataBaseLoader.getData { data ->
            run {
                onDataReady(data)
                dataBaseLoader.close()
            }
        }
    }
}
