package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.DataBaseLoader
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedListRemoteDataSource(
    private val downloadUrlSourceManager: DownloadUrlSourceManager,
    private val dataBaseLoader: DataBaseLoader
) : FeedListDataSource {

    private fun saveFeeds(onRemoteDataReady: () -> Unit) {

        downloadUrlSourceManager.getData("https://habr.com/ru/rss/all/all/") { onRemoteDataReady() }


    }

    override fun getFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {
        //todo check this part on logic
        saveFeeds {
            dataBaseLoader.getData { data ->
                run {
                    onDataReady(data)
                    dataBaseLoader.close()
                }
            }
        }

    }

}


