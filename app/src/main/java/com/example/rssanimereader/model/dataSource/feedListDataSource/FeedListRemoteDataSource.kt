package com.example.rssanimereader.model.dataSource.feedListDataSource

import com.example.rssanimereader.data.web.WebApi
import com.example.rssanimereader.data.dbAPI.contracts.ChannelAndFeedApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FeedListRemoteDataSource(
    private val feedApi: ChannelAndFeedApi,
    private val webApi: WebApi
) {

    fun getFeedsAndChannelFromWeb(linkChannel: String) =
        Single
            .fromCallable { webApi.getFeedsAndChannelFromWeb(linkChannel) }
            .subscribeOn(Schedulers.io())


    fun getFeedsByChannelFromDB(linkChannel: String) =
        Single
            .fromCallable { feedApi.getFeedsByChannel(linkChannel) }
            .subscribeOn(Schedulers.io())


}


