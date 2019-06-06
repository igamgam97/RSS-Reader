package com.example.rssanimereader.model.repository

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.model.dataSource.localDS.LocalDS
import com.example.rssanimereader.model.dataSource.webDS.WebDS
import com.example.rssanimereader.model.repository.contracts.ChannelRepositoryContract
import io.reactivex.Completable
import io.reactivex.Single

class ChannelsRepository(
    val webDS: WebDS,
    val localDS: LocalDS
) : ChannelRepositoryContract {
    override fun isExistChannel(channel: String): Boolean =
        localDS.isExistChannel(channel)

    override fun getChannels(): Single<ArrayList<ChannelItem>> = localDS.getChannels()

    override fun deleteChannels(nameChannel: String): Completable = localDS.deleteChannels(nameChannel)

    override fun retractSaveChannel(channelItem: ChannelItem): Completable = localDS.retractSaveChannel(channelItem)

    override fun getFeedsAndChannelFromWeb(linkChannel: String): Single<Pair<ArrayList<FeedItem>, ChannelItem>> =
        webDS.getFeedsAndChannelFromWeb(linkChannel)

    override fun saveFeedsByChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>):Completable
            = localDS.saveFeedsByChannel(data)
}