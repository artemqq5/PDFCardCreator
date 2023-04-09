package com.crecardbissnes.ebissnescard

import android.app.Application
import com.crecardbissnes.ebissnescard.di.mainDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.INFO)
            androidContext(this@App)
            modules(listOf(mainDI))
        }

    }
}