package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.dbAPI.FeedApi
import com.example.rssanimereader.util.feedUtil.DownloadUrlSourceManager
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FeedListRemoteDataSource(
    private val downloadUrlSourceManager: DownloadUrlSourceManager,
    private val feedApi: FeedApi
) : FeedListDataSource {



    override fun getAllFeeds(onDataReady: (ArrayList<FeedItem>) -> Unit) {
        feedApi.getAllFeeds { data ->
            run {
                onDataReady(data)

            }
        }
    }

    override fun getFeedsByChannel(linkChannel: String): Single<ArrayList<FeedItem>> =
        downloadUrlSourceManager.valideData(linkChannel)
            .flatMap{Single.fromCallable{feedApi.getFeedsByChannel(linkChannel)}}
            .subscribeOn(Schedulers.io())

    fun getAllFeeds() = Single.fromCallable{feedApi.getAllFeeds()}.subscribeOn(Schedulers.io())

}


