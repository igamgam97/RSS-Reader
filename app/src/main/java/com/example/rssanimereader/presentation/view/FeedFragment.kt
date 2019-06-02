package com.example.rssanimereader.presentation.view


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.databinding.FragmentFeedBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.presentation.viewmodel.CommunicateViewModel
import com.example.rssanimereader.presentation.viewmodel.FeedViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class FeedFragment : Fragment() {

    lateinit var communicateViewModel: CommunicateViewModel
    lateinit var feedViewModel : FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        feedViewModel = Injection.provideFeedViewModel(this)
        feedViewModel.shareData.observe(this, Observer {
            startActivity(Intent.createChooser(it, "Share link!"))
        })
        communicateViewModel.selectedFeed.observe(this, Observer {
            feedViewModel.feedItem = it
            feedViewModel.isFavorite = ObservableField(it.itemFavorite)
            feedViewModel.setIsReadFeed()
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = FragmentFeedBinding.inflate(inflater, container, false).apply {
        feedViewModel = this@FeedFragment.feedViewModel
        val selectedFeed = communicateViewModel.selectedFeed.value!!.itemDesc
        /*wvMindorks.settings.loadWithOverviewMode = true*/
        wvMindorks.settings.javaScriptEnabled = true
        wvMindorks.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val code = """javascript:(function() {

        var node = document.createElement('style');

        node.type = 'text/css';
        node.innerHTML = 'body {
            color: c0c0c0;
            background-color: #424242;
        }';

        document.head.appendChild(node);

    })()""".trimIndent()

                wvMindorks.loadUrl(code)
            }
        }
        wvMindorks.setBackgroundColor(Color.parseColor("#424242"))
        wvMindorks.loadData("<style>img{display: inline;height: auto;max-width: 100%;}</style>$selectedFeed", "text/html", "UTF-8")
    }.root

    companion object {
        fun setParams(selectedFeed:FeedItem){

        }
    }

}
