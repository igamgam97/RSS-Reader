package com.example.rssanimereader.model.dataSource.webDS

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.model.dataSource.contracts.IWebDS
import com.example.rssanimereader.model.dataSource.webDS.webApi.web.IWebApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class WebDS(
    private val webApi: IWebApi
):IWebDS {
    override fun getFeedsAndChannelFromWeb(linkChannel: String):Single<Pair<ArrayList<FeedItem>,ChannelItem>> =
        Single
            .fromCallable { webApi.getFeedsAndChannelFromWeb(linkChannel) }
            .subscribeOn(Schedulers.io())
}