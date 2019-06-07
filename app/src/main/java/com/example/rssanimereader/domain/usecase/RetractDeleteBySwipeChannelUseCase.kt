package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.model.repository.ChannelsRepositoryI
import io.reactivex.Completable

class RetractDeleteBySwipeChannelUseCase(private val channelsRepository: ChannelsRepositoryI) {
    operator fun invoke(channelItem: ChannelItem): Completable =
        channelsRepository.retractSaveChannel(channelItem)
}