package com.example.rssanimereader.domain.use_case

import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.data.repository.FeedsRepository
import io.reactivex.Completable

class SetIsFavoriteFeedsUseCase(private val feedsRepository: FeedsRepository) {
    operator fun invoke(feed: FeedItem): Completable =
        feedsRepository.setFavoriteFeed(feed)
}