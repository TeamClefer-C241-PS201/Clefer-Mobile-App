package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityDetailArticleBinding
import com.jimbonlemu.clefer.databinding.ActivityDetailCommentBinding
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailArticleActivity : CoreActivity<ActivityDetailArticleBinding>() {
    private val detailArticleViewModel: ArticleViewModel by viewModel()
    private var isFavorite: Boolean = false
    private var articleId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
        setupViews()
        setupFavoriteButton()
    }

    private fun setupViews() {
        binding.setupToolbar()
    }

    private fun observeData() {
        val extraArticle = intent.getIntExtra(EXTRA_ARTICLE, 0)
        articleId = extraArticle
        detailArticleViewModel.getDetail(extraArticle)
        detailArticleViewModel.detail.observe(this) { article ->
            if (article != null) {
                binding.tvTitleDetail.text = article.articleTitle
                binding.tvDescDetail.text = article.articleDesc
                Glide.with(this@DetailArticleActivity)
                    .load(article.articleImg)
                    .into(binding.ivItemPhotoDetail)
                checkFavorite(Prefs.getUserId.toString())
            }
        }

    }

    private fun setupFavoriteButton() {
        binding.btnSaveBookmarks.setOnClickListener {
            if (isFavorite) {
                deleteFavorite(articleId)
            } else {
                addFavorite(articleId)
            }
        }
    }

    private fun addFavorite(id: Int) {
        val favoriteArticle = FavoriteArticle(
            ownerId = Prefs.getUserId.toString(),
            id = id,
            articleTitle = binding.tvTitleDetail.text.toString(),
            articleDesc = binding.tvDescDetail.text.toString(),
            articleImg = detailArticleViewModel.detail.value?.articleImg ?: ""
        )
        detailArticleViewModel.insertFavoriteArticle(favoriteArticle)
        isFavorite = true
        updateFavoriteUI()
    }

    private fun ActivityDetailArticleBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun deleteFavorite(id: Int) {
        detailArticleViewModel.deleteFavorite(id)
        isFavorite = false
        updateFavoriteUI()
    }

    private fun checkFavorite(ownerId : String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val count = detailArticleViewModel.checkFavoriteById(ownerId)
            withContext(Dispatchers.Main) {
                isFavorite = count > Prefs.getUserId.toString()
                updateFavoriteUI()
            }
        }
    }

    private fun updateFavoriteUI() {
        val iconRes = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        binding.btnSaveBookmarks.setImageResource(iconRes)
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityDetailArticleBinding =
        ActivityDetailArticleBinding.inflate(layoutInflater)
}
