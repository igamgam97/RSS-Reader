package com.example.rssanimereader.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssanimereader.adapter.ChannelRecyclerViewAdapter
import com.example.rssanimereader.databinding.FragmentChannelListBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.viewmodel.ChannelListViewModel
import com.example.rssanimereader.viewmodel.CommunicateViewModel

class ChannelListFragment : Fragment(), ChannelRecyclerViewAdapter.OnItemClickListener {

    private val channelRecyclerViewAdapter = ChannelRecyclerViewAdapter(arrayListOf(), this)
    private lateinit var viewModel: ChannelListViewModel
    private lateinit var communicateViewModel: CommunicateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        viewModel = Injection.provideChannelListViewModel(this)

        viewModel.channels.observe(this, Observer<ArrayList<ChannelItem>> {
            it?.let(channelRecyclerViewAdapter::replaceData)
        })

        viewModel.isAllFeedsButtonClicked.observe(this, Observer {
            onAllFeedsButtonClick()
        })

        viewModel.isFavoriteFeedsButtonClicked.observe(this, Observer {
            onFavoriteFeedsButtonClick()
        })


        communicateViewModel.listOfTypeFragment.observe(activity!!, Observer {
            if (it == ListOfTypeFragment.ChannelListFragment) {
                viewModel.getAllChannels()
            }
        })

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
        communicateViewModel.onFeedListFragmentState()
    }

    override fun onDeleteItemClick(position: Int): Boolean {
        viewModel.deleteChannel(viewModel.channels.value!![position].linkChannel)
        return true
    }

    private fun onAllFeedsButtonClick(){
        communicateViewModel.targetChannel.value = ""
        communicateViewModel.onFeedListFragmentState()
    }

    private fun onFavoriteFeedsButtonClick(){
        communicateViewModel.targetChannel.value = "favorite"
        communicateViewModel.onFeedListFragmentState()
    }


}