package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedsRepository
import io.reactivex.Completable

class SetIsReadUseCase(private val feedsRepository: FeedsRepository) {
    operator fun invoke(feed: FeedItem): Completable = feedsRepository.setIsRead(feed)
}