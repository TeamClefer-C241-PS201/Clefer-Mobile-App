package com.jimbonlemu.clefer.core

import android.app.Application
import com.jimbonlemu.clefer.di.modules.KoinModules.listModule
import com.jimbonlemu.clefer.utils.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class CoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Prefs.init(this)
        startKoin {
            androidLogger()
            androidContext(this@CoreApplication)
            modules(listModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}