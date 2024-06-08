package com.jimbonlemu.clefer.views.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreFragment
import com.jimbonlemu.clefer.databinding.FragmentArticleBinding
import com.jimbonlemu.clefer.views.article.adapter.ArticleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel

class ArticleFragment : CoreFragment<FragmentArticleBinding>() {
    private val articleViewModel: ArticleViewModel by viewModel()
    private val articleAdapter = ArticleAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvArticle.layoutManager = LinearLayoutManager(requireContext())
            rvArticle.adapter = articleAdapter
            // Set up Toolbar
            toolbar.setupToolbar(
                title = getString(R.string.title_article),
                showBackButton = false,
            )
        }

        mainButton()
        getDataAllArticle()
    }

    override fun setupFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentArticleBinding = FragmentArticleBinding.inflate(inflater, container, false)


    private fun mainButton() {
        binding.btnBookmark.setOnClickListener {
            startActivity(Intent(requireContext(), SaveArticleActivity::class.java))
        }
    }

    private fun getDataAllArticle() {
        articleViewModel.getAllArticles.observe(viewLifecycleOwner) { pagingData ->
            articleAdapter.submitData(lifecycle, pagingData)
        }
    }
}

