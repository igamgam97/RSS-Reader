package com.example.rssanimereader.presentation.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssanimereader.adapter.FeedRecyclerViewAdapter
import com.example.rssanimereader.databinding.FeedListFragmentBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.domain.entity.FeedItem
import com.example.rssanimereader.presentation.view.contracts.BaseFragment
import com.example.rssanimereader.presentation.view_model.CommunicateViewModel
import com.example.rssanimereader.presentation.view_model.FeedListViewModel
import java.io.IOException
import java.sql.SQLException
import java.text.ParseException


class FeedListFragment : BaseFragment(), FeedRecyclerViewAdapter.OnItemClickListener {

    private val feedRecyclerViewAdapter =
        FeedRecyclerViewAdapter(arrayListOf(), this)
    private lateinit var communicateViewModel: CommunicateViewModel
    private lateinit var viewModel: FeedListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        viewModel = Injection.provideFeedListViewModel(this)

        viewModel.feeds.observe(this, Observer<ArrayList<FeedItem>> {
            it?.let(feedRecyclerViewAdapter::replaceData)
        })

        viewModel.statusOfSort.observe(this, Observer {
            feedRecyclerViewAdapter.notifyDataSetChanged()
        })

        /*viewModel.progressOfDownload.observe(this, Observer {
            it?.let {
                showProgresOfDownloads(it)
            }

        })*/

        viewModel.statusError.observe(this, Observer {
            it?.let(::showError)
        })
        setData()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        FeedListFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = this@FeedListFragment.viewModel
            executePendingBindings()
            feedRv.layoutManager = LinearLayoutManager(activity)
            feedRv.adapter = feedRecyclerViewAdapter
        }.root


    override fun onItemClick(position: Int) {
        communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)
        viewModel.feeds.value?.get(position)?.let {
            communicateViewModel.onFeedFragmentState(it)
        }
    }

    private fun showError(status: Throwable) {
        when (status) {
            is IOException -> Toast.makeText(context, "Error connection", Toast.LENGTH_SHORT).show()
            is SQLException -> Log.d("bag", "отправка сообщений разработчику")
            is ParseException ->
                Toast.makeText(context, "невозможно прочитать фид из данных навостей", Toast.LENGTH_SHORT).show()
        }

    }

    fun showProgresOfDownloads(value: String) {
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
    }

    fun setParamsFromAddChannelDealogFragment(nameLink: String) {
        Log.d("bag","dff")
        viewModel.channelLink.set(nameLink)
        viewModel.onRefresh()
    }

    fun setParamsFromChannelListFragment(nameLink: String) {
        viewModel.channelLink.set(nameLink)
        viewModel.getFeedsFromCache()
    }

    override fun setData() {
        when(communicateViewModel.mListOfTypeFragment.value){
            ListOfTypeFragment.FeedListFragmentFromChannelListFragment ->{
                viewModel.channelLink.set(communicateViewModel.targetChannel)
                viewModel.getFeedsFromCache()
            }
            ListOfTypeFragment.FeedListFragmentFromAddChannelDialogFragment ->{
                viewModel.channelLink.set(communicateViewModel.searchChannel)
                viewModel.onRefresh()
            }
        }
    }



    //todo retry when (doOnError)
}
