package com.example.rssanimereader.model.dataSource.contracts

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import com.example.rssanimereader.ProvideContextApplication
import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

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