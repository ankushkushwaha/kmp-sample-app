package com.example.kmp_sample_app.android

import android.app.Application
import com.example.kmp_sample_app.database.AppDatabase
import data.Database.CommonKmpHelper
import di.appendWithSharedModule
import data.initKmpStorage
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApp : Application() {
    private lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()

        initKmpStorage(this)
        initKoin()
    }


    fun initKoin() {
        val driverFactory = data.Database.DatabaseDriverFactory(this)

        koinApplication = startKoin {
            /// androidContext(this@MainApp) // to pass context

            modules(appendWithSharedModule(module {
                single { AppDatabase(driverFactory.create()) }
//                factory { AndroidSpecificClass(context: this@KMPApplication) } bind AndroidSpecificClassType::class
            }))
        }
    }
}
