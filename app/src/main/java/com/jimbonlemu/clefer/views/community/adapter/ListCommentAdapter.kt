package com.jimbonlemu.clefer.views.community.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.clefer.databinding.ItemCommentBinding
import com.jimbonlemu.clefer.source.remote.response.CommentDiscussionResponseItem
import com.jimbonlemu.clefer.utils.toTime

class ListCommentAdapter : RecyclerView.Adapter<ListCommentAdapter.ViewHolder>()  {
    private var items: List<CommentDiscussionResponseItem> = emptyList()

    inner class ViewHolder(private val itemBinding: ItemCommentBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(itemComment: CommentDiscussionResponseItem) {
            itemBinding.apply {
                tvItemDesc.text = itemComment.commentBody
                tvDate.text = itemComment.commentDate?.toTime()
                tvLikeCount.text = itemComment.likerCount?.toString()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<CommentDiscussionResponseItem>) {
        items = newItems.sortedByDescending { it.commentDate }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
