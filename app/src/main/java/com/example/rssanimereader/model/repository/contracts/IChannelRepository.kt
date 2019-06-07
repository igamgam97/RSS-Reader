package com.example.rssanimereader.model.repository.contracts

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import io.reactivex.Completable
import io.reactivex.Single

interface IChannelRepository{
    fun isExistChannel(channel: String): Boolean
    fun getChannels(): Single<ArrayList<ChannelItem>>
    fun deleteChannels(nameChannel: String): Completable
    fun retractSaveChannel(channelItem: ChannelItem): Completable
    fun getFeedsAndChannelFromWeb(linkChannel: String): Single<Pair<ArrayList<FeedItem>, ChannelItem>>
    fun saveFeedsByChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>):Completable
}