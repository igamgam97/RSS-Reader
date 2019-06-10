package com.example.rssanimereader.domain.use_case

import com.example.rssanimereader.data.repository.ChannelsRepository
import com.example.rssanimereader.domain.entity.ChannelItem
import io.reactivex.Single

class DeleteChannelsUseCase(private val channelsRepository: ChannelsRepository) {
    operator fun invoke(channelLink: String): Single<ArrayList<ChannelItem>> =
        channelsRepository.deleteChannels(channelLink)
            .andThen(channelsRepository.getChannels())
}