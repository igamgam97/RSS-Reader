package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.service.DownloadUrlSourceManager
import com.example.rssanimereader.service.RemoteDataSaver
import com.example.rssanimereader.util.dbAPI.FeedApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FeedListRemoteDataSource(
    private val downloadUrlSourceManager: DownloadUrlSourceManager,
    private val feedApi: FeedApi,
    private val remoteDataSaver: RemoteDataSaver
) : FeedListDataSource {





    override fun getFeedsByChannel(linkChannel: String): Single<ArrayList<FeedItem>> =
        downloadUrlSourceManager.validateData(linkChannel)
            .flatMap { Single.fromCallable { feedApi.getFeedsByChannel(linkChannel) } }
            .subscribeOn(Schedulers.io())

    override fun getAllFeeds() = Single.fromCallable { feedApi.getAllFeeds() }.subscribeOn(Schedulers.io())


    fun getFeedsByChannel2(linkChannel: String) =
        Observable
            .fromCallable { remoteDataSaver.getFeedsAndChannel(linkChannel) }
            .subscribeOn(Schedulers.io())

    fun saveFeedsByChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>) = remoteDataSaver.saveDataApi(data)

    fun getAllFeeds2()  = remoteDataSaver.getAllFeedsApi()

}


