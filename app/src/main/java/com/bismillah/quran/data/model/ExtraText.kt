package com.bismillah.quran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qosimsha_text")
data class ExtraText (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "qosimsha")
    val extraId: Int,

    @ColumnInfo(name = "text")
    val text: Int,

    @ColumnInfo(name = "lower")
    val lower: String
)