package com.example.rssanimereader.view


import android.os.Bundle
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
import com.example.rssanimereader.viewmodel.MainViewModel


class FeedListFragment : Fragment(), FeedRecyclerViewAdapter.OnItemClickListener {
    private val feedRecyclerViewAdapter = FeedRecyclerViewAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentFeedListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_feed_list, container, false
        )
        //todo к чему прикрепить теперь
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.feedRv.layoutManager = LinearLayoutManager(activity)
        binding.feedRv.adapter = feedRecyclerViewAdapter
        viewModel.feeds.observe(this, Observer<ArrayList<FeedItem>> {
            it?.let {data->
                feedRecyclerViewAdapter.replaceData(data)
            }
        })
        return binding.root
    }

    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
