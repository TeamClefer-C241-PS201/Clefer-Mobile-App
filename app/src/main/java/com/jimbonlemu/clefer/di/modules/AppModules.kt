package com.jimbonlemu.clefer.di.modules

import com.jimbonlemu.clefer.source.local.LocalDataSource
import com.jimbonlemu.clefer.source.remote.RemoteDataSource
import com.jimbonlemu.clefer.source.remote.network.ApiConfig
import org.koin.dsl.module

val AppModules = module {
    single {
        ApiConfig.provideApiService
    }
    single { RemoteDataSource(get()) }
    single { LocalDataSource() }

}