package com.example.rssanimereader.data.dataSource.localDS.dbAPI.contracts

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem



interface IChannelAndFeedApi{
    fun getAllFeeds(whereClause: String? = null, whereArgs: Array<String>? = null): ArrayList<FeedItem>
    val count: Long
    fun insertFeeds(item: FeedItem): Long
    fun insertAllFeedsByChannel(items: List<FeedItem>, channel: String)
    fun setFavoriteFeed(item: FeedItem): Long
    fun setIsReadFeed(item: FeedItem): Long
    fun deleteFeedsByChannel(channel: String): Long
    fun getFeedsByChannel(channel: String): ArrayList<FeedItem>
    fun getFavoriteFeeds(): ArrayList<FeedItem>

    fun getAllUrlChannels(): ArrayList<String>
    fun insertChannel(channel: ChannelItem): Long
    fun deleteChannel(channelLink: String): Long
    fun getAllChannels(): ArrayList<ChannelItem>
    fun isExistChannel(channel: String): Boolean

    fun saveFeedsAndChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>)
}