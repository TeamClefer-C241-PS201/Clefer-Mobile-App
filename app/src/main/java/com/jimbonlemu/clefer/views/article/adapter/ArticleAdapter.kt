package com.jimbonlemu.clefer.views.article.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.databinding.ItemArticleBinding
import com.jimbonlemu.clefer.source.remote.response.ArticlesItem
import com.jimbonlemu.clefer.views.article.DetailArticleActivity

class ArticleAdapter : PagingDataAdapter<ArticlesItem, ArticleAdapter.ArticleViewHolder>(LIST_ARTICLE_DIFF_CALLBACK) {
    inner class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(articleItem: ArticlesItem) {
            binding.tvTitle.text = articleItem.title
            binding.tvItemDesc.text = articleItem.description
            Glide.with(binding.ivItemPhoto)
                .load(articleItem.urlToImage)
                .into(binding.ivItemPhoto)
//            binding.ItemStory.setOnClickListener {
//                val intent = Intent(it.context, DetailArticleActivity::class.java)
//                intent.putExtra(DetailArticleActivity.EXTRA_ARTICLE, articleItem)
//                itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    companion object {
        val LIST_ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}