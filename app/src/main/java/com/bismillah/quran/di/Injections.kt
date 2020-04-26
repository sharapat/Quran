package com.bismillah.quran.di

import androidx.room.Room
import com.bismillah.quran.data.QuranDatabase
import com.bismillah.quran.ui.translation.TranslationListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single {  Room
                .databaseBuilder(androidContext(),
                QuranDatabase::class.java,
                "base.db"
                )
                .createFromAsset("base.db")
                .build()
    }
    single { get<QuranDatabase>().quranDao() }
}

val viewModelModule = module {
    viewModel { TranslationListViewModel(get()) }
}