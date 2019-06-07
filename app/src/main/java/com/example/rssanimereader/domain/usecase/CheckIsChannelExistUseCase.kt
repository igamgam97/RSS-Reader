package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.model.repository.ChannelsRepositoryI

class CheckIsChannelExistUseCase(private val channelsRepository: ChannelsRepositoryI) {
    operator fun invoke(channelLink: String): Boolean =
        channelsRepository.isExistChannel((channelLink))
}

