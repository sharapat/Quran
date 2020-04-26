package com.bismillah.quran

import android.app.Application
import com.bismillah.quran.di.dataModule
import com.bismillah.quran.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class QuranApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val modules = listOf(dataModule, viewModelModule)
        startKoin { // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@QuranApp)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(modules) }
    }
}