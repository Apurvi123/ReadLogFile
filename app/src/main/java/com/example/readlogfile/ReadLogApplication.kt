package com.example.readlogfile

import android.app.Application
import com.example.readlogfile.koin.readModules
import com.example.readlogfile.koin.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReadLogApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ReadLogApplication)
            modules(listOf(readModules, retrofitModule))
        }
    }
}