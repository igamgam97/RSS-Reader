package com.example.rssanimereader.model

import com.example.rssanimereader.data.web.WebApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class WebDS(
    private val webApi: WebApi
) {
    fun getFeedsAndChannelFromWeb(linkChannel: String) =
        Single
            .fromCallable { webApi.getFeedsAndChannelFromWeb(linkChannel) }
            .subscribeOn(Schedulers.io())
}