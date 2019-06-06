package com.example.rssanimereader.usecase

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.model.repository.ChannelsRepository
import io.reactivex.Completable
import io.reactivex.Single

class DeleteChannelsUseCase(private val channelsRepository: ChannelsRepository) {
    operator fun invoke(channelLink: String): Single<ArrayList<ChannelItem>> =
        channelsRepository.deleteChannels(channelLink)
            .andThen(channelsRepository.getChannels())
}