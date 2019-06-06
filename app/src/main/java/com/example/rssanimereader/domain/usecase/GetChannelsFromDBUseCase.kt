package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.model.repository.ChannelsRepository
import io.reactivex.Single

class GetChannelsFromDBUseCase(private val channelsRepository: ChannelsRepository) {
    operator fun invoke(): Single<ArrayList<ChannelItem>> =
        channelsRepository.getChannels()
}