package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.model.repository.FeedsRepository
import io.reactivex.Single

class GetFeedsFromDBUseCase(private val feedsRepository: FeedsRepository){
    operator fun invoke(linkChannel:String) : Single<ArrayList<FeedItem>> =
        feedsRepository.getFeedsFromCashe(linkChannel)
}