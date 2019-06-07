package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.model.repository.ChannelsRepositoryI
import io.reactivex.Single

class GetChannelsFromDBUseCase(private val channelsRepository: ChannelsRepositoryI) {
    operator fun invoke(): Single<ArrayList<ChannelItem>> =
        channelsRepository.getChannels()
}