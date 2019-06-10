package com.example.rssanimereader.domain.use_case

import com.example.rssanimereader.data.repository.FeedsRepository
import com.example.rssanimereader.domain.entity.FeedItem
import io.reactivex.Single

class GetFeedsFromDBUseCase(private val feedsRepository: FeedsRepository) {
    operator fun invoke(linkChannel: String): Single<ArrayList<FeedItem>> =
        feedsRepository.getFeedsFromCache(linkChannel)
}