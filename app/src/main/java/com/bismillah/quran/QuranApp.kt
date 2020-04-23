package com.bismillah.quran

import android.app.Application
import com.bismillah.quran.di.dataModule
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module

class QuranApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val modules : List<Module> = listOf(dataModule)
        startKoin(applicationContext, modules)
    }
}