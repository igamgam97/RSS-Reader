package com.example.rssanimereader.data.dataSource.localDS

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import com.example.rssanimereader.ProvideContextApplication
import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.data.dataSource.contracts.ILocalDS
import com.example.rssanimereader.data.dataSource.localDS.dbAPI.FeedAndChannelApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class LocalDS(private val feedAndChannelApi: FeedAndChannelApi) : ILocalDS {
    override fun setFavoriteFeed(feed: FeedItem): Completable =
        Completable.fromCallable { feedAndChannelApi.setFavoriteFeed(feed) }.subscribeOn(Schedulers.io())

    override fun setIsRead(feed: FeedItem): Completable =
        Completable.fromCallable { feedAndChannelApi.setIsReadFeed(feed) }.subscribeOn(Schedulers.io())

    override fun getChannels(): Single<ArrayList<ChannelItem>> =
        Single.fromCallable { feedAndChannelApi.getAllChannels() }.subscribeOn(Schedulers.io())

    override fun deleteChannels(nameChannel: String):Completable =
        Completable.fromCallable { feedAndChannelApi.deleteChannel(nameChannel) }

    override fun retractSaveChannel(channelItem: ChannelItem): Completable =
        Completable.fromCallable { feedAndChannelApi.insertChannel(channelItem) }

    override fun isExistChannel(channel: String): Boolean = feedAndChannelApi.isExistChannel(channel)

    override fun getFeedsByChannelFromDB(linkChannel: String) : Single<ArrayList<FeedItem>> =
        Single
            .fromCallable { feedAndChannelApi.getFeedsByChannel(linkChannel) }
            .subscribeOn(Schedulers.io())

    override fun getChannelsLink(): Observable<String> =
        Observable
            .fromIterable(feedAndChannelApi.getAllUrlChannels())
            .subscribeOn(Schedulers.io())

    override fun saveFeedsByChannel(data: Pair<ArrayList<FeedItem>, ChannelItem>):Completable =
        Completable
            .fromCallable { feedAndChannelApi.saveFeedsAndChannel(data) }
            .subscribeOn(Schedulers.io())


    override fun getFeedsByChannel(linkChannel: String):Single<ArrayList<FeedItem>> =
        Single
            .fromCallable<ArrayList<FeedItem>> { feedAndChannelApi.getFeedsByChannel(linkChannel) }
            .subscribeOn(Schedulers.io())

    override fun getAllFeeds():Single<ArrayList<FeedItem>> =
        Single.fromCallable{feedAndChannelApi.getAllFeeds()}.subscribeOn(Schedulers.io())

    override fun getFavoriteFeeds():Single<ArrayList<FeedItem>> =
        Single.fromCallable{feedAndChannelApi.getFavoriteFeeds()}.subscribeOn(Schedulers.io())


    override fun saveImageToInternalStorage(bitmap: Bitmap, path: String): Uri {
        val wrapper = ContextWrapper(ProvideContextApplication.applicationContext())
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "$path.jpg")
        try {
            val stream: OutputStream?
            stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

}