package com.example.rssanimereader.util.feedUtil.parser

import android.annotation.SuppressLint
import android.util.Xml
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.feedUtil.RemoteDataParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.text.ParseException


class RSSRemoteDataParser(private val source: String) : RemoteDataParser<FeedItem> {
    companion object {
        private val ns: String? = null
        private const val TAG_FEED = "rss"
        private const val TAG_CHANNEL = "channel"
        private const val TAG_ITEM = "item"
        private const val TAG_TITLE = "title"
        private const val TAG_DESCRIPTION = "description"
        private const val TAG_LINK = "link"
        private const val TAG_PUBLISHED = "pubDate"
    }


    override fun parse(input: InputStream): List<FeedItem> {
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
    private fun readFeed(parser: XmlPullParser): List<FeedItem> {
        var entries: List<FeedItem> = ArrayList()

        // Search for <feed> tags.
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
            if (parser.name == TAG_CHANNEL) {
                entries = readChannel(parser)
            } else {
                skip(parser)
            }
        }
        return entries
    }

    @Throws(XmlPullParserException::class, IOException::class, ParseException::class)
    private fun readChannel(parser: XmlPullParser): List<FeedItem> {
        val episodes = ArrayList<FeedItem>()

        parser.require(
            XmlPullParser.START_TAG,
            ns,
            TAG_CHANNEL
        )
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == TAG_ITEM) {
                episodes.add(readItem(parser))
            } else {
                skip(parser)
            }
        }

        return episodes
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
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                TAG_TITLE -> title = readBasicTag(
                    parser,
                    TAG_TITLE
                )
                TAG_DESCRIPTION -> subtitle = readBasicTag(
                    parser,
                    TAG_DESCRIPTION
                )
                TAG_LINK -> link = readBasicTag(
                    parser,
                    TAG_LINK
                )
                TAG_PUBLISHED -> publishedDate = readBasicTag(
                    parser,
                    TAG_PUBLISHED
                )
                else -> skip(parser)
            }
        }
        return FeedItem(
            title!!,
            subtitle!!,
            link!!,
            publishedDate!!,
            source
        )
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
