package com.bismillah.quran.di

import androidx.room.Room
import com.bismillah.quran.data.QuranDatabase
import org.koin.dsl.module.module

val dataModule = module {
    single {  Room
                .databaseBuilder(get(),
                QuranDatabase::class.java,
                "base.db"
                )
                .createFromAsset("base.db")
                .build()
    }
    single { get<QuranDatabase>().quranDao() }
}