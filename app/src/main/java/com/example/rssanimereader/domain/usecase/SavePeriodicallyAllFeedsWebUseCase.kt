package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.model.repository.ChannelsRepositoryI
import com.example.rssanimereader.model.repository.IFeedsRepository
import com.example.rssanimereader.util.NetManager
import io.reactivex.Completable
import java.io.IOException

class SavePeriodicallyAllFeedsWebUseCase(
    private val feedsRepository: IFeedsRepository,
    private val channelsRepository: ChannelsRepositoryI,
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
        throw IOException()
    }

}