package com.example.rssanimereader.model.repository

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.WebDS
import com.example.rssanimereader.model.dataSource.LocalDS
import com.example.rssanimereader.web.WebApi
import io.reactivex.Completable
import io.reactivex.Single

class ChannelsRepository(
    val webDS: WebDS,
    val localDS: LocalDS
) {
    fun isExistChannel(channel: String): Boolean =
        localDS.isExistChannel(channel)

    fun getChannels(): Single<ArrayList<ChannelItem>> = localDS.getChannels()

    fun deleteChannels(nameChannel: String): Completable = localDS.deleteChannels(nameChannel)

    fun retractSaveChannel(channelItem: ChannelItem): Completable = localDS.retractSaveChannel(channelItem)

    fun getFeedsAndChannelFromWeb(linkChannel: String) = webDS.getFeedsAndChannelFromWeb(linkChannel)

    fun saveFeedsByChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>) = localDS.saveFeedsByChannel(data)
}