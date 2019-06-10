package com.example.rssanimereader.domain.use_case

import com.example.rssanimereader.data.repository.ChannelsRepository
import com.example.rssanimereader.domain.entity.ChannelItem
import io.reactivex.Completable

class RetractDeleteBySwipeChannelUseCase(private val channelsRepository: ChannelsRepository) {
    operator fun invoke(channelItem: ChannelItem): Completable =
        channelsRepository.retractSaveChannel(channelItem)
}