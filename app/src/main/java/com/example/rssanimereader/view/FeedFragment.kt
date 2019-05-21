package com.example.rssanimereader.view


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.databinding.FragmentFeedBinding
import com.example.rssanimereader.di.FeedListViewModelFactory
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.FeedListViewModel
import com.example.rssanimereader.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.feed_view_layout.*
import kotlinx.android.synthetic.main.fragment_feed.view.*


/**
 * A simple [Fragment] subclass.
 *
 */
class FeedFragment : Fragment() {

    lateinit var feedViewModelFactory: ViewModelProvider.Factory
    lateinit var communicateViewModel: CommunicateViewModel
    lateinit var feedViewModel : FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedViewModelFactory = Injection.provideViewModelFactory(this)
        feedViewModel = ViewModelProviders.of(this, feedViewModelFactory)
            .get(FeedViewModel::class.java)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = FragmentFeedBinding.inflate(inflater, container, false).apply {
        val communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        feedViewModel = this@FeedFragment.feedViewModel
        feedViewModel?.feedItem =  communicateViewModel.selectedFeed
        val selectedFeed = communicateViewModel.selectedFeed.itemDesc

        wvMindorks.settings.loadWithOverviewMode = true
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
        wvMindorks.loadData(selectedFeed, "text/html", "UTF-8")
    }.root


}
