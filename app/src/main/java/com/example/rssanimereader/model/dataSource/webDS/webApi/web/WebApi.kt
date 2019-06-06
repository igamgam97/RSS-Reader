package com.example.rssanimereader.model.dataSource.webDS.webApi.web

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.model.dataSource.webDS.webApi.web.contracts.FeedAndChannelParserContract
import com.example.rssanimereader.model.dataSource.webDS.webApi.web.contracts.WebApiContract
import java.io.InputStream

class WebApi (
    private val feedAndChannelParser: FeedAndChannelParserContract,
    private val imageSaver: NewImageSaver
) : WebApiContract{


    override fun getFeedsAndChannelFromWeb(urlPath: String): Pair<ArrayList<FeedItem>, ChannelItem> {
        val streamFromURLLoader = StreamFromURLLoader()
        streamFromURLLoader(urlPath).inputStream.use {
            /* val data = RSSRemoteDataParser().parse(it,urlPath)*/
            val data = feedAndChannelParser.parse(it, urlPath)
            return data
        }
    }


    private fun downloadImage(urlPath: String): Bitmap {
        val streamFromURLLoader = StreamFromURLLoader()
        streamFromURLLoader(urlPath).inputStream.use {
            return BitmapFactory.decodeStream(it)
        }
    }



}

interface RemoteDataParser {
    fun parse(input: InputStream, source: String): Pair<ArrayList<FeedItem>, ChannelItem>
}