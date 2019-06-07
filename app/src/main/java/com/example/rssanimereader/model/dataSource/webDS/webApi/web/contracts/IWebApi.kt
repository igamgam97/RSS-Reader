package com.example.rssanimereader.model.dataSource.webDS.webApi.web.contracts

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem

interface IWebApi{
    fun getFeedsAndChannelFromWeb(urlPath: String): Pair<ArrayList<FeedItem>, ChannelItem>
}