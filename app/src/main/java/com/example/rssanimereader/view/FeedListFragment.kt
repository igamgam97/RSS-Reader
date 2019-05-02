package com.example.rssanimereader.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssanimereader.FeedRecyclerViewAdapter
import com.example.rssanimereader.R
import com.example.rssanimereader.databinding.FragmentFeedListBinding
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.FeedListViewModel


class FeedListFragment : Fragment(), FeedRecyclerViewAdapter.OnItemClickListener {

    private val feedRecyclerViewAdapter = FeedRecyclerViewAdapter(arrayListOf(), this)
    lateinit var viewModel: FeedListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(FeedListViewModel::class.java)
        viewModel.feeds.observe(this, Observer<ArrayList<FeedItem>> {
            it?.let(feedRecyclerViewAdapter::replaceData)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = (DataBindingUtil.inflate(
            inflater, R.layout.fragment_feed_list, container, false
        )
                as FragmentFeedListBinding)
        // don't work with apply and with
        binding.viewModel = viewModel
        with(binding) {
            executePendingBindings()
            feedRv.layoutManager = LinearLayoutManager(activity)
            feedRv.adapter = feedRecyclerViewAdapter
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        val communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        communicateViewModel.onFeedFramentState()
        communicateViewModel.selectedFeed = viewModel.feeds.value!![position]
    }
}
