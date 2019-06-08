package com.example.rssanimereader.domain.use_case

import com.example.rssanimereader.data.repository.ChannelsRepository
import com.example.rssanimereader.data.repository.FeedsRepository
import com.example.rssanimereader.exception.NoInternetException
import com.example.rssanimereader.util.NetManager
import io.reactivex.Completable
import java.io.IOException

class SavePeriodicallyAllFeedsWebUseCase(
    private val feedsRepository: FeedsRepository,
    private val channelsRepository: ChannelsRepository,
    private val netManager: NetManager
) {
    operator fun invoke(): Completable =
        feedsRepository.getChannelsLinkFromDB("")
            .concatMapCompletable { link -> getFeedsFromWeb(link) }

    private fun getFeedsFromWeb(linkChannel: String) =
        netManager
            .hasInternetConnection()
            .flatMap { hasInternet -> getFeedsByChannelFromWebApi(linkChannel, hasInternet) }
            .flatMapCompletable { data -> channelsRepository.saveFeedsByChannel(data) }

    private fun getFeedsByChannelFromWebApi(linkChannel: String, hasInternet: Boolean) = if (hasInternet) {
        channelsRepository.getFeedsAndChannelFromWeb(linkChannel)
    } else {
        throw NoInternetException()
    }

}