package com.example.rssanimereader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rssanimereader.databinding.RvItemChannelBinding
import com.example.rssanimereader.entity.ChannelItem

class ChannelRecyclerViewAdapter(
        private var items: ArrayList<ChannelItem>,
        private var listener: OnItemClickListener
) : RecyclerView.Adapter<ChannelRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemChannelBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    fun replaceData(items: ArrayList<ChannelItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteItemClick(position: Int): Boolean
    }


    class ViewHolder(private var binding: RvItemChannelBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(channel: ChannelItem, listener: OnItemClickListener?) {
            binding.channel = channel
            if (listener != null) binding.root.setOnClickListener { listener.onItemClick(layoutPosition) }
            if (listener != null) binding.root.setOnLongClickListener { listener.onDeleteItemClick(layoutPosition) }
            binding.executePendingBindings()
        }
    }
}