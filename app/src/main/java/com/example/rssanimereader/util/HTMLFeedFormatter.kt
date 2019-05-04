package com.example.rssanimereader.util

import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.util.feedUtil.parser.HTMLFormatter
import java.util.regex.Pattern

class HTMLFeedFormatter : HTMLFormatter<FeedItem> {
    override fun generateHtml(item: FeedItem): String {

        val pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>")

        val m = pattern.matcher(item.itemDesc)

        if (m.find()) {
            val src = m.group(1)
        }

        val description = item.itemDesc.replace(pattern.toRegex(), "")

        return ("<h2>" + item.itemTitle + "</h2>"
                + "<hr>"
                + "<p>" + description + "</p>"
                + "<hr>"
                + "<p style='text-align: right'><i>" + item.itemPubDate + "</i></p>"
                + "<hr>")
    }
}

