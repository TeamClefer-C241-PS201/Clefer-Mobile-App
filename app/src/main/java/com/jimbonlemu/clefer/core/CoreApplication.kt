package com.jimbonlemu.clefer.core

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.jimbonlemu.clefer.di.modules.AppModules
import com.jimbonlemu.clefer.di.modules.repositoryModules
import com.jimbonlemu.clefer.di.modules.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class CoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        startKoin {
            androidLogger()
            androidContext(this@CoreApplication)
            modules(listOf(
                viewModelModules,
                repositoryModules,
                AppModules
            ))
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}