package com.bismillah.quran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ayatlar")
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