package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.model.repository.ChannelsRepository
import com.example.rssanimereader.model.repository.FeedsRepository
import com.example.rssanimereader.util.NetManager
import java.io.IOException

class SavePeriodicallyAllFeedsWebUseCase(
    private val feedsRepository: FeedsRepository,
    private val channelsRepository: ChannelsRepository,
    private val netManager: NetManager
) {
    operator fun invoke() =
        feedsRepository.getChannelsFromDB("")
            .concatMapCompletable { link -> getFeedsFromWeb(link)}

    private fun getFeedsFromWeb(linkChannel: String) =
        netManager
            .hasInternetConnection()
            .flatMap { hasInternet -> getFeedsByChannelFromWebApi(linkChannel, hasInternet) }
            .flatMapCompletable { data -> channelsRepository.saveFeedsByChannel(data) }

    private fun getFeedsByChannelFromWebApi(linkChannel: String, hasInternet: Boolean) = if (hasInternet) {
        channelsRepository.getFeedsAndChannelFromWeb(linkChannel)
    } else {
        throw IOException()
    }

}