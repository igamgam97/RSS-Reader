package com.example.rssanimereader.util.feedUtil.parser

import android.annotation.SuppressLint
import android.util.Log
import android.util.Xml
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.feedUtil.RemoteDataParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.text.ParseException


class RSSRemoteDataParser(
        private val source: String,
        private val htmlFormatter: HTMLFormatter<FeedItem>
) : RemoteDataParser {
    private companion object {
        val ns: String? = null
        const val TAG_FEED = "rss"
        const val TAG_CHANNEL = "channel"
        const val TAG_IMAGE = "image"
        const val TAG_ITEM = "item"
        const val TAG_TITLE = "title"
        const val TAG_CHANNEL_URL_IMAGE = "url"
        const val TAG_DESCRIPTION = "description"
        const val TAG_LINK = "link"
        const val TAG_PUBLISHED = "pubDate"
        const val TAG__CHANNEL_TITLE = "title"
        val TAG_ENCLOSURE = "enclosure"

    }


    override fun parse(input: InputStream): Pair<ArrayList<FeedItem>, ChannelItem> {
        input.use {
            val parser = Xml.newPullParser().apply {
                setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                setInput(input, null)
            }
            parser.nextTag()
            return this@RSSRemoteDataParser.readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class, ParseException::class)
    private fun readFeed(parser: XmlPullParser): Pair<ArrayList<FeedItem>, ChannelItem> {
        var pair: Pair<ArrayList<FeedItem>, ChannelItem>? = null

        parser.require(
                XmlPullParser.START_TAG,
                ns,
                TAG_FEED
        )
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Look for the <channel> tag.
            when (parser.name) {
                TAG_CHANNEL -> pair = readChannel(parser)
                else -> skip(parser)
            }
        }
        return pair!!
    }

    @Throws(XmlPullParserException::class, IOException::class, ParseException::class)
    private fun readChannel(parser: XmlPullParser): Pair<ArrayList<FeedItem>, ChannelItem> {
        val episodes = ArrayList<FeedItem>()
        var channelImageURl: String? = null
        var channelTitle: String? = null
        parser.require(
                XmlPullParser.START_TAG,
                ns,
                TAG_CHANNEL
        )
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                TAG_ITEM -> episodes.add(readItem(parser))
                TAG_IMAGE -> channelImageURl = readImageItem(parser)
                TAG__CHANNEL_TITLE -> channelTitle = readBasicTag(
                        parser,
                        TAG__CHANNEL_TITLE
                )
                else -> skip(parser)
            }
        }
        val channel = ChannelItem(source, channelTitle!!, channelImageURl!!)

        return Pair(episodes, channel)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(XmlPullParserException::class, IOException::class, ParseException::class)
    private fun readItem(parser: XmlPullParser): FeedItem {
        parser.require(
                XmlPullParser.START_TAG,
                ns,
                TAG_ITEM
        )
        var title: String? = null
        var subtitle: String? = null
        var link: String? = null
        var publishedDate: String? = null
        var tempLink: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                TAG_TITLE -> title = readBasicTag(
                        parser,
                        TAG_TITLE
                )
                TAG_DESCRIPTION -> {
                    subtitle = readBasicTag(
                            parser,
                            TAG_DESCRIPTION
                    )
                }
                TAG_LINK -> link = readBasicTag(
                        parser,
                        TAG_LINK
                )
                TAG_PUBLISHED -> publishedDate = readBasicTag(
                        parser,
                        TAG_PUBLISHED
                )

                TAG_ENCLOSURE -> tempLink = readEnclosure(parser)

                else -> skip(parser)
            }
        }
        val feedItem = FeedItem(
                title!!,
                subtitle!!,
                link!!,
                publishedDate!!,
                false,
                source
        )
        val formatedSubtitle = htmlFormatter.generateHtml(feedItem)
        /*  val formatedSubtitle = subtitle*/
        //Log.d("bag", formatedSubtitle)
        return FeedItem(
                title,
                formatedSubtitle,
                link,
                publishedDate,
                false,
                source
        )
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(XmlPullParserException::class, IOException::class, ParseException::class)
    private fun readImageItem(parser: XmlPullParser): String? {
        parser.require(
                XmlPullParser.START_TAG,
                ns,
                TAG_IMAGE
        )
        var imageURL: String? = ""
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                TAG_CHANNEL_URL_IMAGE -> imageURL = readBasicTag(
                        parser,
                        TAG_CHANNEL_URL_IMAGE
                )
                else -> skip(parser)
            }
        }
        return imageURL
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readBasicTag(parser: XmlPullParser, tag: String): String? {
        parser.require(
                XmlPullParser.START_TAG,
                ns, tag
        )
        val result = readText(parser)
        parser.require(
                XmlPullParser.END_TAG,
                ns, tag
        )
        return result
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String? {
        var result: String? = null
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readEnclosure(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, TAG_ENCLOSURE)
        val link = parser.getAttributeValue(null, "url")
                ?: throw XmlPullParserException("Failed to parse <enclosure>")
        parser.nextTag()
        return link
    }

    /**
     * Skips tags until the corresponding END_TAG.
     */
    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }


}
