package com.example.rssanimereader.usecase

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.model.repository.ChannelsRepository
import io.reactivex.Completable

class RetractDeleteBySwipeChannelUseCase(private val channelsRepository: ChannelsRepository){
    operator fun invoke(channelItem: ChannelItem): Completable = channelsRepository.retractSaveChannel(channelItem)
}