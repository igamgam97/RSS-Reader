package com.example.rssanimereader.model.dataSource

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.util.dbAPI.ChannelAndFeedApi
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ChannelListDataSource(private val channelApi: ChannelAndFeedApi) {

    fun getChannels(): Single<ArrayList<ChannelItem>> =
        Single.fromCallable { channelApi.getAllChannels() }.subscribeOn(Schedulers.io())

    fun deleteChannels(nameChannel: String) =
        Completable.fromCallable { channelApi.deleteChannel(nameChannel) }

    fun retractSaveChannel(channelItem: ChannelItem): Completable =
        Completable.fromCallable { channelApi.insertChannel(channelItem) }

}