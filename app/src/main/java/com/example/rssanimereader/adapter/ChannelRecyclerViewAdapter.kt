package com.example.rssanimereader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rssanimereader.databinding.ChannelItemRvBinding
import com.example.rssanimereader.domain.entity.ChannelItem

class ChannelRecyclerViewAdapter(
    private var channels: ArrayList<ChannelItem>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<ChannelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ChannelItemRvBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(channels[position], listener)

    override fun getItemCount(): Int = channels.size

    fun replaceData(items: ArrayList<ChannelItem>) {
        this.channels = items
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        channels.removeAt(position)
        notifyItemRemoved(position)
    }

    fun add(position: Int, item: ChannelItem) {
        channels.add(position, item)
        notifyItemInserted(position)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    class ViewHolder(private var binding: ChannelItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(channel: ChannelItem, listener: OnItemClickListener?) {
            binding.channel = channel
            if (listener != null) binding.root.setOnClickListener { listener.onItemClick(layoutPosition) }
            binding.executePendingBindings()
        }
    }
}