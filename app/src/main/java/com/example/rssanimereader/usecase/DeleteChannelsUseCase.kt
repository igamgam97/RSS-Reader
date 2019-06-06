package com.example.rssanimereader.usecase

import com.example.rssanimereader.model.repository.ChannelsRepository
import io.reactivex.Completable

class DeleteChannelsUseCase(private val channelsRepository: ChannelsRepository) {
    operator fun invoke(channelLink: String): Completable =
        channelsRepository.deleteChannels(channelLink)
}