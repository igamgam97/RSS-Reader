package com.example.rssanimereader.data.dataSource.webDS.webApi.web

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.contracts.IFeedAndChannelParser
import com.example.rssanimereader.data.dataSource.webDS.webApi.web.contracts.IWebApi
import com.example.rssanimereader.domain.entity.ChannelItem
import com.example.rssanimereader.domain.entity.FeedItem
import java.io.InputStream

class IWebApi(
    private val IFeedAndChannelParser: IFeedAndChannelParser,
    private val imageSaver: NewImageSaver
) : IWebApi {


    override fun getFeedsAndChannelFromWeb(urlPath: String): Pair<ArrayList<FeedItem>, ChannelItem> {
        val streamFromURLLoader = StreamFromURLLoader()
        streamFromURLLoader(urlPath).inputStream.use {
            /* val data = RSSRemoteDataParserI().parse(it,urlPath)*/
            return IFeedAndChannelParser.parse(it, urlPath)
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