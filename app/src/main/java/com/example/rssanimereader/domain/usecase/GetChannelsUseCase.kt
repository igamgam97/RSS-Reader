package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.model.repository.ChannelsRepository
import io.reactivex.Single

class GetChannelsUseCase(private val channelsRepository: ChannelsRepository) {
    operator fun invoke(): Single<ArrayList<ChannelItem>> =
        channelsRepository.getChannels()
}