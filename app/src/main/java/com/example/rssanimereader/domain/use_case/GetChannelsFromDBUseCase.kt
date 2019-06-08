package com.example.rssanimereader.domain.use_case

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.data.repository.ChannelsRepository
import io.reactivex.Single

class GetChannelsFromDBUseCase(private val channelsRepository: ChannelsRepository) {
    operator fun invoke(): Single<ArrayList<ChannelItem>> =
        channelsRepository.getChannels()
}