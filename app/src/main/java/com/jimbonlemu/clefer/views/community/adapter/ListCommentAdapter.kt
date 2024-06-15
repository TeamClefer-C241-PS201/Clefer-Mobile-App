package com.jimbonlemu.clefer.views.community.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.clefer.databinding.ItemCommentBinding
import com.jimbonlemu.clefer.source.remote.response.CommentDiscussionResponseItem
import com.jimbonlemu.clefer.utils.toTime
import com.jimbonlemu.clefer.views.community.DetailCommentActivity

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

                ivLike.setOnClickListener {
                    // Tangani aksi suka
                }

                root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailCommentActivity::class.java)
                    intent.putExtra(DetailCommentActivity.POST_ID, itemComment.postId)
                    itemView.context.startActivity(intent)
                }
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
