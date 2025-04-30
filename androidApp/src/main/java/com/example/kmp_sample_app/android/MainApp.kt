package com.example.kmp_sample_app.android

import android.app.Application
import di.appendWithSharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApp : Application() {
    private lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()

        initKoin()
//        initKoinFromShared()
    }

    fun initKoin() {
        koinApplication = startKoin {
            androidContext(this@MainApp)

            modules(appendWithSharedModule(module {
                // Add Android specific modules here
            }))
        }
    }
}
