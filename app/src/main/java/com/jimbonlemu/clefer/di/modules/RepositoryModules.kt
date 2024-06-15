package com.jimbonlemu.clefer.di.modules

import com.jimbonlemu.clefer.repository.AppRepository
import org.koin.dsl.module

val repositoryModules = module {
    single { AppRepository(get(), get()) }
}