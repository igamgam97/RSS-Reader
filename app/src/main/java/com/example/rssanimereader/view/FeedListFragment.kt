package com.example.rssanimereader.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssanimereader.adapter.FeedRecyclerViewAdapter
import com.example.rssanimereader.databinding.FragmentFeedListBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.FeedListViewModel


class FeedListFragment : Fragment(), FeedRecyclerViewAdapter.OnItemClickListener {

    private val feedRecyclerViewAdapter =
        FeedRecyclerViewAdapter(arrayListOf(), this)
    lateinit var communicateViewModel: CommunicateViewModel
    lateinit var viewModel: FeedListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("bag","onCreate")
        communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)


        viewModel = Injection.provideFeedListViewModel(this)
        viewModel.feeds.observe(this, Observer<ArrayList<FeedItem>> {
            it?.let(feedRecyclerViewAdapter::replaceData)
        })
        communicateViewModel.targetChannel.observe(this, Observer {
            viewModel.getFeedsByChannel(communicateViewModel.targetChannel.value!!)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        FragmentFeedListBinding.inflate(inflater, container, false).apply {
            viewModel = this@FeedListFragment.viewModel
            executePendingBindings()
            feedRv.layoutManager = LinearLayoutManager(activity)
            feedRv.adapter = feedRecyclerViewAdapter


        }.root

    override fun onItemClick(position: Int) {
        val communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        communicateViewModel.onFeedFragmentState()
        communicateViewModel.selectedFeed = viewModel.feeds.value!![position]
    }
}
