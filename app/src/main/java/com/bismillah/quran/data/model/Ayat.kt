package com.bismillah.quran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ayatlar")
data class Ayat(

    @ColumnInfo(name = "sure")
    var sureId: Int,

    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "lower")
    var lower: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Int? = 0,

    @ColumnInfo(name = "sure_name")
    var sureName: String?="",

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int

)