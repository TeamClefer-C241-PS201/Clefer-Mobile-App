package com.jimbonlemu.clefer.di.modules

import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import com.jimbonlemu.clefer.views.auth.viewmodels.AuthViewModels
import com.jimbonlemu.clefer.views.community.viewmodel.CommunityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { ArticleViewModel(get()) }
    viewModel { AuthViewModels(get()) }
    viewModel {CommunityViewModel(get())}
}