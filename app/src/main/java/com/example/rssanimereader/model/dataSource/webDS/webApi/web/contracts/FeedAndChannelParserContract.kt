package com.example.rssanimereader.model.dataSource.webDS.webApi.web.contracts

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import java.io.InputStream

interface FeedAndChannelParserContract{
    fun parse(input: InputStream, source: String): Pair<ArrayList<FeedItem>, ChannelItem>
}