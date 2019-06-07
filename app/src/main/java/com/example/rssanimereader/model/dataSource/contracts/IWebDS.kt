package com.example.rssanimereader.model.dataSource.contracts

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import io.reactivex.Single

interface IWebDS {
    fun getFeedsAndChannelFromWeb(linkChannel: String): Single<Pair<ArrayList<FeedItem>, ChannelItem>>
}