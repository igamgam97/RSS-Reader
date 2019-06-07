package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.model.repository.ChannelsRepositoryI
import com.example.rssanimereader.model.repository.IFeedsRepository
import com.example.rssanimereader.util.NetManager
import io.reactivex.Observable
import java.io.IOException

class GetFeedsFromWebUseCase(
    private val feedsRepository: IFeedsRepository,
    private val channelsRepository: ChannelsRepositoryI,
    private val netManager: NetManager
) {
    operator fun invoke(linkChannel: String): Observable<Pair<String, ArrayList<FeedItem>>> =
        feedsRepository.getChannelsLinkFromDB(linkChannel)
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