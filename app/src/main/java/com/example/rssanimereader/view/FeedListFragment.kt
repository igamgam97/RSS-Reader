package com.example.rssanimereader.view


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
import com.example.rssanimereader.databinding.FragmentFeedListBinding
import com.example.rssanimereader.di.Injection
import com.example.rssanimereader.entity.FeedItem
import com.example.rssanimereader.viewmodel.CommunicateViewModel
import com.example.rssanimereader.viewmodel.FeedListViewModel
import java.io.IOException
import java.sql.SQLException
import java.text.ParseException


class FeedListFragment : Fragment(), FeedRecyclerViewAdapter.OnItemClickListener {

    private val feedRecyclerViewAdapter =
        FeedRecyclerViewAdapter(arrayListOf(), this)
    lateinit var communicateViewModel: CommunicateViewModel
    lateinit var viewModel: FeedListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communicateViewModel = ViewModelProviders.of(activity!!).get(CommunicateViewModel::class.java)

        viewModel = Injection.provideFeedListViewModel(this)
        viewModel.feeds.observe(this, Observer<ArrayList<FeedItem>> {
            it?.let{
                feedRecyclerViewAdapter.replaceData(it)
                Log.d("bag",it.size.toString())
            }
        })
        communicateViewModel.targetChannel.observe(this, Observer {
            viewModel.channelLink = it ?: ""

            viewModel.getFeedsFromCashe()
        })

        communicateViewModel.searchChannel.observe(this, Observer {
            viewModel.channelLink = it ?: ""
            viewModel.onRefresh()
        })

        viewModel.statusOfSort.observe(this, Observer {
            feedRecyclerViewAdapter.notifyDataSetChanged()
        })

        viewModel.statusError.observe(this, Observer {
            it?.let{
                showError(it)
            }
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
        viewModel.feeds.value?.get(position)?.let {
            communicateViewModel.onFeedFragmentState(it)
        }
    }

    private fun showError(status:Throwable) {
        when(status){
            is IOException -> Toast.makeText(context, "Error connection", Toast.LENGTH_SHORT).show()
            is SQLException -> Log.d("bag","отправка сообщений разработчику")
            is ParseException ->
                Toast.makeText(context, "невозможно прочитать фид из данных навостей", Toast.LENGTH_SHORT).show()
        }

    }
    //todo fratmap to datasave
    //todo добавить InvalidUrlException
    //todo retry when (doOnError)
}
