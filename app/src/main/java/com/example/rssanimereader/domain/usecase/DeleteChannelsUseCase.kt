package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.model.repository.ChannelsRepositoryI
import io.reactivex.Single

class DeleteChannelsUseCase(private val channelsRepository: ChannelsRepositoryI) {
    operator fun invoke(channelLink: String): Single<ArrayList<ChannelItem>> =
        channelsRepository.deleteChannels(channelLink)
            .andThen(channelsRepository.getChannels())
}