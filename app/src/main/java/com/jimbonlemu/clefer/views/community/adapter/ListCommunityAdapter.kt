package com.jimbonlemu.clefer.views.community.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.clefer.databinding.ItemCommunityBinding
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.utils.toTime

class ListCommunityAdapter : RecyclerView.Adapter<ListCommunityAdapter.ViewHolder>() {

    private val items: MutableList<AllDiscussionResponseItem> = mutableListOf()

    inner class ViewHolder(private val itemBinding: ItemCommunityBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AllDiscussionResponseItem) {
            itemBinding.apply {
                tvName.text = item.postTitle
                tvItemDesc.text = item.postDesc
                tvDate.text = item.postDate.toTime()
                tvLikeCount.text = item.postLike.toString()

                // Example of handling like button click
                ivLike.setOnClickListener {
                    // handleLikeClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommunityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newItems: List<AllDiscussionResponseItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    // Example function to handle like button click
    /*private fun handleLikeClick(item: AllDiscussionResponseItem) {
        // Implement like functionality here
    }*/
}
