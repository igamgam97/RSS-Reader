package com.example.rssanimereader.model.dataSource

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.util.dbAPI.ChannelAPI
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ChannelListDataSource(private val channelApi: ChannelAPI) {
    fun getChannels() =
        Single.fromCallable { channelApi.getChannels() }.subscribeOn(Schedulers.io())

    fun deleteChannels(nameChannel: String) =
        Single.fromCallable { channelApi.deleteChannel(nameChannel) }

    fun retractSaveChannel(channelItem: ChannelItem) =
            Completable.fromCallable{channelApi.retractSaveChannel(channelItem)}

}