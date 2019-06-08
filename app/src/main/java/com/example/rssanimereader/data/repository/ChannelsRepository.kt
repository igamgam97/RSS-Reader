package com.example.rssanimereader.data.repository

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.data.dataSource.contracts.ILocalDS
import com.example.rssanimereader.data.dataSource.contracts.IWebDS
import com.example.rssanimereader.data.repository.contracts.IChannelRepository
import io.reactivex.Completable
import io.reactivex.Single

class ChannelsRepository(
    val webDS: IWebDS,
    val localDS: ILocalDS
) : IChannelRepository {
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