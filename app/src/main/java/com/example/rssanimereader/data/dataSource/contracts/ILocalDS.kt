package com.example.rssanimereader.data.dataSource.contracts

import android.graphics.Bitmap
import android.net.Uri
import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ILocalDS{
    fun setFavoriteFeed(feed: FeedItem): Completable
    fun setIsRead(feed: FeedItem): Completable
    fun getChannels(): Single<ArrayList<ChannelItem>>
    fun deleteChannels(nameChannel: String): Completable
    fun retractSaveChannel(channelItem: ChannelItem): Completable
    fun isExistChannel(channel: String): Boolean
    fun getFeedsByChannelFromDB(linkChannel: String):Single<ArrayList<FeedItem>>
    fun getChannelsLink(): Observable<String>
    fun saveFeedsByChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>):Completable
    fun getFeedsByChannel(linkChannel: String):Single<ArrayList<FeedItem>>
    fun getAllFeeds() :Single<ArrayList<FeedItem>>
    fun getFavoriteFeeds():Single<ArrayList<FeedItem>>
    fun saveImageToInternalStorage(bitmap: Bitmap, path: String): Uri
}