package com.bismillah.quran.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bismillah.quran.data.model.*

@Database(entities = [Ayat::class, Explanation::class, Extra::class, ExtraText::class, QuranText::class,
    QuranTitle::class, Sure::class], version = 1, exportSchema = false)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao
}