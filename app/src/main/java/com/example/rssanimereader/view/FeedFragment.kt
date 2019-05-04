package com.example.rssanimereader.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.FragmentFeedBinding
import com.example.rssanimereader.util.HTMLFeedFormater
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.FeedListViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class FeedFragment : Fragment() {

    lateinit var viewModel: FeedListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProviders.of(activity!!).get(FeedListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFeedBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_feed, container, false
        )
        val communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        val selectedFeed = communicateViewModel.selectedFeed.itemDesc
        binding.wvMindorks.settings.loadWithOverviewMode = true
        binding.wvMindorks.loadData(selectedFeed, "text/html", "UTF-8")

        return binding.root
    }


}
