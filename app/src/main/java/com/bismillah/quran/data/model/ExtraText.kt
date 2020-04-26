package com.bismillah.quran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qosimsha_text")
data class ExtraText (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "qosimsha")
    var extraId: Int,

    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "lower")
    var lower: String
)