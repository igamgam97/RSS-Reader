package com.example.rssanimereader.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.FragmentFeedBinding
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.viewmodel.MainViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class FeedFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFeedBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_feed, container, false
        )
        viewModel.getSelected().observe(this, Observer<FeedItem> {
            it?.let {
                binding.wvMindorks.settings.loadWithOverviewMode = true
                binding.wvMindorks.settings.useWideViewPort = true
                binding.wvMindorks.loadData(it.itemDesc, "text/html", "UTF-8")
                Log.d("bag", it.itemDesc)
            }
        })
        binding.viewModel = viewModel
        return binding.root
    }


}
