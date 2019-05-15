package com.example.rssanimereader.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssanimereader.ChannelRecyclerViewAdapter
import com.example.rssanimereader.databinding.FragmentChannelListBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.viewmodel.ChannelListViewModel
import com.example.rssanimereader.viewmodel.CommunicateViewModel

class ChannelListFragment : Fragment(), ChannelRecyclerViewAdapter.OnItemClickListener {

    private val channelRecyclerViewAdapter = ChannelRecyclerViewAdapter(arrayListOf(), this)
    private lateinit var viewModel: ChannelListViewModel
    private lateinit var channelListViewModelFactory: ViewModelProvider.Factory
    private lateinit var communicateViewModel: CommunicateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        channelListViewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, channelListViewModelFactory)
                .get(ChannelListViewModel::class.java)

        viewModel.channels.observe(this, Observer<ArrayList<ChannelItem>> {
            it?.let(channelRecyclerViewAdapter::replaceData)
        })

        communicateViewModel.listOfTypeFragment.observe(activity!!, Observer {
            if (it.peek() == ListOfTypeFragment.ChannelListFragment) {
                viewModel.getAllChannels()
            }
        })

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            FragmentChannelListBinding.inflate(inflater, container, false).apply {
                executePendingBindings()
                feedRv.layoutManager = LinearLayoutManager(activity)
                feedRv.adapter = channelRecyclerViewAdapter
                channelListViewModel = viewModel
            }.root

    override fun onItemClick(position: Int) {
        communicateViewModel.targetChannel.value = viewModel.channels.value!![position].linkChannel
        Log.d("bag", communicateViewModel.targetChannel.value.toString())
        communicateViewModel.onFeedListFramgentState()
    }

    override fun onDeleteItemClick(position: Int): Boolean {
        viewModel.deleteChannel(viewModel.channels.value!![position].linkChannel)
        return true
    }
}