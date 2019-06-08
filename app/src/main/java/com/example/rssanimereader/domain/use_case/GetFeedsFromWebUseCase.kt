package com.example.rssanimereader.domain.use_case

import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.data.repository.ChannelsRepository
import com.example.rssanimereader.data.repository.FeedsRepository
import com.example.rssanimereader.exception.NoInternetException
import com.example.rssanimereader.util.NetManager
import io.reactivex.Observable
import java.io.IOException

class GetFeedsFromWebUseCase(
    private val feedsRepository: FeedsRepository,
    private val channelsRepository: ChannelsRepository,
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
        throw NoInternetException()
    }

}