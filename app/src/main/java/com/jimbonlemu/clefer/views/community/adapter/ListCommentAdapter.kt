package com.jimbonlemu.clefer.views.community.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ItemCommentBinding
import com.jimbonlemu.clefer.source.remote.response.CommentDiscussionResponseItem
import com.jimbonlemu.clefer.utils.toTime

class ListCommentAdapter(private val listener: OnCommentLikeButtonClickListener? = null) :
    RecyclerView.Adapter<ListCommentAdapter.ViewHolder>() {
    private var items: List<CommentDiscussionResponseItem> = emptyList()

    inner class ViewHolder(private val itemBinding: ItemCommentBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(itemComment: CommentDiscussionResponseItem) {
            itemBinding.apply {
                tvItemDesc.text = itemComment.commentBody
                tvDate.text = itemComment.commentDate?.toTime()
                tvItemName.text = itemComment.name
                tvLikeCount.text = itemComment.likerCount?.toString()
                updateLikeIcon(itemComment.likeStat == 1)
                btnLike.setOnClickListener {
                    handleLikeClick(itemComment)
                }
            }
        }

        private fun handleLikeClick(itemComment: CommentDiscussionResponseItem) {
            if (itemComment.likeStat == 1) {
                itemComment.likeStat = 0
                itemComment.likerCount = itemComment.likerCount?.minus(1)
                updateLikeIcon(false)
            } else {
                itemComment.likeStat = 1
                itemComment.likerCount = itemComment.likerCount?.plus(1)
                updateLikeIcon(true)
            }
            itemBinding.tvLikeCount.text = itemComment.likerCount?.toString()
            listener?.onCommentLikeButtonClicked(itemComment.postId!!, itemComment.commentId!!)
            notifyItemChanged(adapterPosition)
        }

        private fun updateLikeIcon(isLiked: Boolean) {
            val likeIcon = if (isLiked) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            itemBinding.btnLike.setImageResource(likeIcon)
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

    interface OnCommentLikeButtonClickListener {
        fun onCommentLikeButtonClicked(postId: Int, commentId: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
