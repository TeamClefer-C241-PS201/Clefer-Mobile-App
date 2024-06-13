package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ActivityDetailArticleBinding
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding
    private val detailArticleViewModel: ArticleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.detail_artikel_title),
            showBackButton = true,
            backAction = { onBackPressedDispatcher.onBackPressed() }
        )
        val extraArticle = intent.getIntExtra(EXTRA_ARTICLE, 0)
        detailArticleViewModel.getDetail(extraArticle)
        detailArticleViewModel.detail.observe(this) { article ->
            if (article != null) {
                binding.tvTitleDetail.text = article.articleTitle
                binding.tvDescDetail.text = article.articleDesc
                Glide.with(this@DetailArticleActivity)
                    .load(article.articleImg)
                    .into(binding.ivItemPhotoDetail)
            }
        }
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}
