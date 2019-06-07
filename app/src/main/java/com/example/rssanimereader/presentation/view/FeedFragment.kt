package com.example.rssanimereader.presentation.view


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.databinding.FeedFragmentBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.presentation.view.contracts.BaseFragment
import com.example.rssanimereader.presentation.view_model.CommunicateViewModel
import com.example.rssanimereader.presentation.view_model.FeedViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class FeedFragment : BaseFragment() {

    private lateinit var communicateViewModel: CommunicateViewModel
    private lateinit var feedViewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("bag", "onCreate")
        communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        feedViewModel = Injection.provideFeedViewModel(this)
        feedViewModel.shareData.observe(this, Observer {
            startActivity(Intent.createChooser(it, "Share link!"))
        })
        communicateViewModel.selectedFeed?.let {
            feedViewModel.feedItem = ObservableField(it)
            feedViewModel.isFavorite.set(it.itemFavorite)
            feedViewModel.setIsReadFeed()
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FeedFragmentBinding.inflate(inflater, container, false).apply {
        feedViewModel = this@FeedFragment.feedViewModel
    }.root


    fun setParams(selectedFeed: FeedItem?) {
        selectedFeed?.let {
            feedViewModel.feedItem = ObservableField(selectedFeed)
            feedViewModel.isFavorite.set(selectedFeed.itemFavorite)
            feedViewModel.setIsReadFeed()
        }
    }

    override fun setData() {
        communicateViewModel.selectedFeed?.let {selectedFeed ->
            feedViewModel.feedItem = ObservableField(selectedFeed)
            feedViewModel.isFavorite.set(selectedFeed.itemFavorite)
            feedViewModel.setIsReadFeed()
        }
    }


}
