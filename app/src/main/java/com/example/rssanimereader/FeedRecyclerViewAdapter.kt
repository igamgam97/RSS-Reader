package com.example.rssanimereader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rssanimereader.databinding.RvItemFeedBinding
import com.example.rssanimereader.entity.FeedItem

class FeedRecyclerViewAdapter(
        private var items: ArrayList<FeedItem>,
        private var listener: OnItemClickListener
) : RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemFeedBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    fun replaceData(items: ArrayList<FeedItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(private var binding: RvItemFeedBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(feed: FeedItem, listener: OnItemClickListener?) {
            binding.feed = feed
            if (listener != null) binding.root.setOnClickListener { listener.onItemClick(layoutPosition) }

            binding.executePendingBindings()
        }
    }
}
