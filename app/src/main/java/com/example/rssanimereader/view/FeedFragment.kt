package com.example.rssanimereader.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.rssanimereader.databinding.FragmentFeedBinding
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.FeedListViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class FeedFragment : Fragment() {

    lateinit var viewModel: FeedListViewModel
    lateinit var communicateViewModel: CommunicateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?=FragmentFeedBinding.inflate(inflater, container, false).apply {
        val communicateViewModel=ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        val selectedFeed=communicateViewModel.selectedFeed.itemDesc
        wvMindorks.settings.loadWithOverviewMode=true
        wvMindorks.loadData(selectedFeed, "text/html", "UTF-8")
    }.root


}
