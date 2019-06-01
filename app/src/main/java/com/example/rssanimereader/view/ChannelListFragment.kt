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
import com.example.rssanimereader.adapter.ChannelRecyclerViewAdapter
import com.example.rssanimereader.adapter.SwipeHandler
import com.example.rssanimereader.databinding.FragmentChannelListBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.entity.ChannelItem
import com.example.rssanimereader.viewmodel.ChannelListViewModel
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction



class ChannelListFragment : Fragment(), ChannelRecyclerViewAdapter.OnItemClickListener, SwipeHandler {

    private val channelRecyclerViewAdapter = ChannelRecyclerViewAdapter(arrayListOf(), this)
    private lateinit var viewModel: ChannelListViewModel
    private lateinit var communicateViewModel: CommunicateViewModel
    private lateinit var addChannelDialogFragment: AddChannelDialogFragment
    private lateinit var binding:FragmentChannelListBinding
    lateinit var tempItem: Pair<Int, ChannelItem>

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

        viewModel.isAddChannelButtonClicked.observe(this, Observer {
            onAddChannelButtonClick()
        })

        addChannelDialogFragment= AddChannelDialogFragment()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) :View {
        binding = FragmentChannelListBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
            feedRv.layoutManager = LinearLayoutManager(activity)
            feedRv.adapter = channelRecyclerViewAdapter
            channelListViewModel = viewModel
            handler = this@ChannelListFragment
        }
        return binding.root
    }
    override fun onItemClick(position: Int) {
        communicateViewModel.onFeedListFragmentState(viewModel.channels.value!![position].linkChannel)
    }

    override fun onDeleteItemClick(position: Int): Boolean {
        viewModel.deleteChannel(viewModel.channels.value!![position].linkChannel)
        return true
    }

    private fun onAllFeedsButtonClick(){
        communicateViewModel.onFeedListFragmentState("")
    }

    private fun onFavoriteFeedsButtonClick(){
        communicateViewModel.onFeedListFragmentState("favorite")
    }


    private fun onAddChannelButtonClick(){
        Log.d("bag","bad")
        addChannelDialogFragment.show(fragmentManager!!, "dil")
    }

    override fun onItemSwipedLeft(position: Int) {
        saveAndRemoveItem(position)
        showSnackbar("Swiped Left $position")
    }

    override fun onItemSwipedRight(position: Int) {
        saveAndRemoveItem(position)
        showSnackbar("Swiped Right $position")
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG)
            .setAction("UNDO"){
                    retractSavedItem()
            }.show()
    }

    private fun retractSavedItem() {
        viewModel.retractSaveChannel(tempItem.second)
        channelRecyclerViewAdapter.add(tempItem.first, tempItem.second)
    }

    private fun saveAndRemoveItem(position: Int) {
        tempItem = Pair(position, viewModel.channels.value!![position])
        viewModel.deleteChannel( viewModel.channels.value!![position].linkChannel)
        channelRecyclerViewAdapter.remove(position)
        communicateViewModel.targetChannel.value = ""
    }




}