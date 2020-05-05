package com.bismillah.quran.di

import android.content.Context
import androidx.room.Room
import com.bismillah.quran.Settings
import com.bismillah.quran.data.QuranDatabase
import com.bismillah.quran.ui.ayat.AyatListAdapter
import com.bismillah.quran.ui.ayat.AyatListViewModel
import com.bismillah.quran.ui.ayatoriginal.OriginalAyatRVAdapter
import com.bismillah.quran.ui.ayatoriginal.OriginalAyatViewModel
import com.bismillah.quran.ui.explanation.ayat.AyatExplanationViewModel
import com.bismillah.quran.ui.explanation.sure.SureExplanationListAdapter
import com.bismillah.quran.ui.explanation.sure.SureExplanationViewModel
import com.bismillah.quran.ui.favorites.FavoriteListAdapter
import com.bismillah.quran.ui.favorites.FavoriteListViewModel
import com.bismillah.quran.ui.sure.SureListViewModel
import org.koin.android.ext.koin.androidApplication
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
    single { androidApplication().applicationContext
        .getSharedPreferences("com.bismillah.quran.preferences", Context.MODE_PRIVATE) }
    single { Settings(get()) }
}

val adapterModule = module {
    single { AyatListAdapter(get()) }
    single { OriginalAyatRVAdapter(get()) }
    single { SureExplanationListAdapter(get()) }
    single { FavoriteListAdapter(get()) }
}

val viewModelModule = module {
    viewModel { SureListViewModel(get()) }
    viewModel { AyatListViewModel(get()) }
    viewModel { AyatExplanationViewModel(get()) }
    viewModel { OriginalAyatViewModel(get()) }
    viewModel { SureExplanationViewModel(get()) }
    viewModel { FavoriteListViewModel(get()) }
}