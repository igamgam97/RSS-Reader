package com.example.rssanimereader.domain.use_case

import com.example.rssanimereader.data.repository.ChannelsRepository

class CheckIsChannelExistUseCase(private val channelsRepository: ChannelsRepository) {
    operator fun invoke(channelLink: String): Boolean =
        channelsRepository.isExistChannel((channelLink))
}

