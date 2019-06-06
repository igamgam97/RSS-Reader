package com.example.rssanimereader.domain.usecase

import com.example.rssanimereader.model.repository.FeedsRepository

class GetFeedsFromDBUseCase(private val feedsRepository: FeedsRepository){
    operator fun invoke(linkChannel:String) = feedsRepository.getFeedsFromCashe(linkChannel)
}