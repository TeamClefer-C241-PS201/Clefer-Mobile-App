package com.jimbonlemu.clefer.views.navbar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.FragmentArticleBinding
import com.jimbonlemu.clefer.views.article.adapter.ArticleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel

class ArticleFragment : Fragment() {
    private val articleViewModel : ArticleViewModel by viewModel()
    private val articleAdapter = ArticleAdapter()
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticle.adapter = articleAdapter
        // Set up Toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.toolbar_artikel),
            showBackButton = false,
        )
        getDataAllArticle()
        return root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDataAllArticle(){
        articleViewModel.getAllArticles.observe(viewLifecycleOwner){ pagingData ->
            articleAdapter.submitData(lifecycle, pagingData)
        }
    }
}

