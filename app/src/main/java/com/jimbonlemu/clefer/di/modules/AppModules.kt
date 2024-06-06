package com.jimbonlemu.clefer.di.modules

import com.jimbonlemu.clefer.source.remote.network.ApiConfig
import org.koin.dsl.module

val AppModules = module {
    single {
        ApiConfig.provideApiService
    }

}