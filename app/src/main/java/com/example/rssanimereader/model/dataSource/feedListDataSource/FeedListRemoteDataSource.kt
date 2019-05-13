package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.FeedApi
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager

class FeedListRemoteDataSource(
        private val downloadUrlSourceManager: DownloadUrlSourceManager,
        private val feedApi: FeedApi
) : FeedListDataSource {

    private fun saveFeeds(linkChannel: String, onRemoteDataReady: () -> Unit) {

        downloadUrlSourceManager.getData(linkChannel) { onRemoteDataReady() }

    }

    override fun getFeedsByChannel(linkChannel: String, onDataReady: (ArrayList<FeedItem>) -> Unit) {
        //todo check this part on logic
        saveFeeds(linkChannel) {
            feedApi.getFeedsByChannel(linkChannel) { data ->
                run {
                    onDataReady(data)
                    downloadUrlSourceManager.onDisconnect()
                }
            }
        }
    }

    override fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {
        feedApi.getAllFeeds { data ->
            run {
                onDataReady(data)

            }
        }
    }


}


