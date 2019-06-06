package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.model.repository.ChannelsRepository

class CheckIsChannelExistUseCase(private val channelsRepository: ChannelsRepository) {
    operator fun invoke(channelLink: String): Boolean =
        channelsRepository.isExistChannel((channelLink))
}

