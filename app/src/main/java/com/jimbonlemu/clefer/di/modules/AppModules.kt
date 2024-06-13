package com.jimbonlemu.clefer.di.modules

import androidx.room.Room
import com.jimbonlemu.clefer.source.local.AppDatabase
import com.jimbonlemu.clefer.source.local.LocalDataSource
import com.jimbonlemu.clefer.source.remote.RemoteDataSource
import com.jimbonlemu.clefer.source.remote.network.ApiConfig
import com.jimbonlemu.clefer.utils.Constant
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val AppModules = module {
    single {
        ApiConfig.provideApiService
    }
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            Constant.DB_NAME
        ).allowMainThreadQueries().build()
    }

}