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

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int

)