package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.model.repository.IFeedsRepository
import io.reactivex.Completable

class SetIsReadUseCase(private val feedsRepository: IFeedsRepository) {
    operator fun invoke(feed: FeedItem): Completable =
        feedsRepository.setIsRead(feed)
}