package com.example.rssanimereader.usecase

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedsRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class SetIsFavoriteFeedsUseCase(private val feedsRepository: FeedsRepository) {
    operator fun invoke(feed: FeedItem): Completable = feedsRepository.setFavoriteFeed(feed)
}