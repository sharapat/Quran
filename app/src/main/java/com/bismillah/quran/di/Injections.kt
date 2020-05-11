package com.bismillah.quran.di

import android.content.Context
import androidx.room.Room
import com.bismillah.quran.Settings
import com.bismillah.quran.data.QuranDatabase
import com.bismillah.quran.ui.translation.ayat.AyatListAdapter
import com.bismillah.quran.ui.translation.ayat.AyatListViewModel
import com.bismillah.quran.ui.translation.original.OriginalAyatRVAdapter
import com.bismillah.quran.ui.translation.original.OriginalAyatViewModel
import com.bismillah.quran.ui.explanation.ayat.AyatExplanationViewModel
import com.bismillah.quran.ui.explanation.sure.SureExplanationListAdapter
import com.bismillah.quran.ui.explanation.sure.SureExplanationViewModel
import com.bismillah.quran.ui.favorites.FavoriteListAdapter
import com.bismillah.quran.ui.favorites.FavoriteListViewModel
import com.bismillah.quran.ui.information.content.InfoTextAdapter
import com.bismillah.quran.ui.information.content.InfoViewModel
import com.bismillah.quran.ui.information.title.InfoListViewModel
import com.bismillah.quran.ui.search.SearchAdapter
import com.bismillah.quran.ui.search.SearchViewModel
import com.bismillah.quran.ui.splash.SplashViewModel
import com.bismillah.quran.ui.translation.sure.SureListViewModel
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
    single { InfoTextAdapter(get()) }
    single { SearchAdapter(get()) }
}

val viewModelModule = module {
    viewModel { SureListViewModel(get()) }
    viewModel { AyatListViewModel(get()) }
    viewModel { AyatExplanationViewModel(get()) }
    viewModel { OriginalAyatViewModel(get()) }
    viewModel { SureExplanationViewModel(get()) }
    viewModel { FavoriteListViewModel(get()) }
    viewModel { InfoListViewModel(get()) }
    viewModel { InfoViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SplashViewModel(get(), get()) }
}