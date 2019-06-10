package com.example.rssanimereader.util

import com.example.rssanimereader.domain.entity.FeedItem
import java.util.regex.Pattern

class FeedFormatter {
    fun generateHtml(feedItem: FeedItem): String =
        if (feedItem.itemDesc.isHTML())
            ("<h2>" + feedItem.itemTitle + "</h2>"
                    + "<hr>"
                    + "<p>" + feedItem.itemDesc + "</p>"
                    + "<hr>"
                    + "<p style='text-align: right'><i>" + feedItem.itemPubDate + "</i></p>"
                    + "<hr>")
        else
            ("<h2>" + feedItem.itemTitle + "</h2>"
                    + "<hr>"
                    + "<p>" + feedItem.itemDesc + "</p>"
                    + "<br>"
                    + "<a href = ${feedItem.itemLink}>Читать далее</a>"
                    + "<hr>"
                    + "<p style='text-align: right'><i>" + feedItem.itemPubDate + "</i></p>"
                    + "<hr>")
}


private fun String.isHTML(): Boolean {
    val tagStart = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>"
    val tagEnd = "\\</\\w+\\>"
    val tagSelfClosing = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>"
    val htmlEntity = "&[a-zA-Z][a-zA-Z0-9]+;"
    val htmlPattern: Pattern = Pattern.compile(
        "($tagStart.*$tagEnd)|($tagSelfClosing)|($htmlEntity)",
        Pattern.DOTALL
    )
    return htmlPattern.matcher(this).find()
}