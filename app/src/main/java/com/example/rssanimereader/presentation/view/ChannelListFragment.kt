package com.example.rssanimereader.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssanimereader.adapter.ChannelRecyclerViewAdapter
import com.example.rssanimereader.databinding.FragmentChannelListBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.presentation.viewmodel.ChannelListViewModel
import com.example.rssanimereader.presentation.viewmodel.CommunicateViewModel
import com.google.android.material.snackbar.Snackbar


class ChannelListFragment : Fragment(), ChannelRecyclerViewAdapter.OnItemClickListener{

    private val channelRecyclerViewAdapter = ChannelRecyclerViewAdapter(arrayListOf(), this)
    private lateinit var viewModel: ChannelListViewModel
    private lateinit var communicateViewModel: CommunicateViewModel
    private lateinit var addChannelDialogFragment: AddChannelDialogFragment
    private lateinit var binding: FragmentChannelListBinding
    lateinit var tempItem: Pair<Int, ChannelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("bag", "onCreateChannel")
        communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        viewModel = Injection.provideChannelListViewModel(this)

        viewModel.channels.observe(this, Observer<ArrayList<ChannelItem>> {
            it?.let(channelRecyclerViewAdapter::replaceData)
        })

        viewModel.isTypeButtonClicked.observe(this, Observer {
            isTypeOfButtonClicked(it)
        })

        addChannelDialogFragment = AddChannelDialogFragment()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChannelListBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
            feedRv.layoutManager = LinearLayoutManager(activity)
            feedRv.adapter = channelRecyclerViewAdapter
            channelListViewModel = viewModel
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true//toggle this
    }

    override fun onItemClick(position: Int) {
        communicateViewModel.onFeedListFragmentState(viewModel.channels.value!![position].linkChannel)
    }


    private fun onAllFeedsButtonClick() {
        communicateViewModel.onFeedListFragmentState("")
    }

    private fun onFavoriteFeedsButtonClick() {
        communicateViewModel.onFeedListFragmentState("favorite")
    }


    private fun onAddChannelButtonClick() {
        addChannelDialogFragment.show(fragmentManager!!, "dialog")
    }


    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                retractSavedItem()
            }.show()
    }

    private fun retractSavedItem() {
        viewModel.retractSaveChannel(viewModel.tempItem.second)
        channelRecyclerViewAdapter.add(viewModel.tempItem.first, viewModel.tempItem.second)
    }

    fun onSwipeLeft() {
        channelRecyclerViewAdapter.remove(viewModel.positionOnDelete)
        communicateViewModel.targetChannel = ""
        showSnackbar("Swiped Left $viewModel.positionOnDelete")
    }

    fun onSwipeRight() {
        channelRecyclerViewAdapter.remove(viewModel.positionOnDelete)
        communicateViewModel.targetChannel = ""
        showSnackbar("Swiped Left $viewModel.positionOnDelete")
    }

    private fun isTypeOfButtonClicked(typeOfButtonChannelListFragment: TypeOfButtonChannelListFragment) {
        when (typeOfButtonChannelListFragment) {
            TypeOfButtonChannelListFragment.ShowAddChannelDialogFragment -> onAddChannelButtonClick()
            TypeOfButtonChannelListFragment.ShowFavoriteFeeds -> onFavoriteFeedsButtonClick()
            TypeOfButtonChannelListFragment.ShowAllFeeds -> onAllFeedsButtonClick()
            TypeOfButtonChannelListFragment.SwipeRight -> onSwipeRight()
            TypeOfButtonChannelListFragment.SwipeLeft -> onSwipeLeft()
        }
    }
    fun setParams() {
        viewModel.getAllChannels()
    }

}

enum class TypeOfButtonChannelListFragment {
    ShowAddChannelDialogFragment,
    ShowAllFeeds,
    ShowFavoriteFeeds,
    SwipeRight,
    SwipeLeft
}