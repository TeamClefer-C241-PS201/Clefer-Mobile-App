package com.jimbonlemu.clefer.views.community.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ItemCommunityBinding
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.utils.toTime
import com.jimbonlemu.clefer.views.community.DetailCommentActivity

class ListCommunityAdapter(private val listener: OnLikeButtonClickListener? = null) :
    RecyclerView.Adapter<ListCommunityAdapter.ViewHolder>() {

    private var items: List<AllDiscussionResponseItem> = emptyList()

    inner class ViewHolder(private val itemBinding: ItemCommunityBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AllDiscussionResponseItem) {
            itemBinding.apply {
                tvName.text = item.postTitle
                tvItemDesc.text = item.postDesc
                tvDate.text = item.postDate?.toTime()
                tvLikeCount.text = item.likerCount?.toString()
                tvCommentCount.text = item.commentCount?.toString()
                updateLikeIcon(item.likeStat == 1)
                btnLike.setOnClickListener {
                    handleLikeClick(item)
                }

                root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailCommentActivity::class.java)
                    intent.putExtra(DetailCommentActivity.POST_ID, item.postId)
                    itemView.context.startActivity(intent)
                }
            }
        }

        private fun handleLikeClick(item: AllDiscussionResponseItem) {
            if (item.likeStat == 1) {
                item.likeStat = 0
                item.likerCount = item.likerCount?.minus(1)
                updateLikeIcon(false)
            } else {
                item.likeStat = 1
                item.likerCount = item.likerCount?.plus(1)
                updateLikeIcon(true)
            }
            itemBinding.tvLikeCount.text = item.likerCount?.toString()
            listener?.onLikeButtonClicked(item)
            notifyItemChanged(adapterPosition)
        }

        private fun updateLikeIcon(isLiked: Boolean) {
            val likeIcon = if (isLiked) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            itemBinding.btnLike.setImageResource(likeIcon)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<AllDiscussionResponseItem>) {
        items = newItems.sortedByDescending { it.postDate }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCommunityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    interface OnLikeButtonClickListener {
        fun onLikeButtonClicked(item: AllDiscussionResponseItem)
    }

}



