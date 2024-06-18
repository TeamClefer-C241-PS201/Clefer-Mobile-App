package com.jimbonlemu.clefer.di.modules

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

object KoinModules {
  val listModule = listOf(
        AppModules,
        repositoryModules,
        viewModelModules
    )
    fun reloadModule() {
        unloadKoinModules(listModule)
        loadKoinModules(listModule)
    }
}