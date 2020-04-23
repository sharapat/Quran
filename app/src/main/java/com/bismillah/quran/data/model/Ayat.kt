package com.bismillah.quran.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Ayat(

    @ColumnInfo(name = "sure")
    val sureId: Int,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "lower")
    val lower: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int

)