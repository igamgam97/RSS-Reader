package com.example.rssanimereader.usecase

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.ChannelsRepository
import com.example.rssanimereader.model.repository.FeedsRepository
import com.example.rssanimereader.util.NetManager
import io.reactivex.Single
import java.io.IOException

class GetFeedsFromWebUseCase(
    private val feedsRepository: FeedsRepository,
    private val channelsRepository: ChannelsRepository,
    private val netManager: NetManager
    ) {
    operator fun invoke(linkChannel: String) =
        feedsRepository.getChannelsFromDB(linkChannel)
            .concatMapSingle { link -> getFeedsFromWeb(link).map { link to it } }

    private fun getFeedsFromWeb(linkChannel: String) =
        netManager
            .hasInternetConnection()
            .flatMap { hasInternet -> getFeedsByChannelFromWebApi(linkChannel, hasInternet) }
            .flatMap { data ->
                channelsRepository.saveFeedsByChannel(data)
                    .andThen(feedsRepository.getFeedsByChannelFromDB(data.second.linkChannel))
            }

    private fun getFeedsByChannelFromWebApi(linkChannel: String, hasInternet: Boolean) = if (hasInternet) {
        channelsRepository.getFeedsAndChannelFromWeb(linkChannel)
    } else {
        throw IOException()
    }

}