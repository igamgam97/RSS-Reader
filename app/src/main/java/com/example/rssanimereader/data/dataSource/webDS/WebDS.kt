package com.example.rssanimereader.data.dataSource.webDS

import com.example.rssanimereader.data.dataSource.contracts.IWebDS
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.IWebApi
import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class WebDS(
    private val webApi: IWebApi
) : IWebDS {
    override fun getFeedsAndChannelFromWeb(linkChannel: String): Single<Pair<ArrayList<FeedItem>, ChannelItem>> =
        Single
            .fromCallable { webApi.getFeedsAndChannelFromWeb(linkChannel) }
            .subscribeOn(Schedulers.io())
}