package com.bismillah.quran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tusindirmeler")
data class Explanation(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name="sure")
    var sureId: Int,

    @ColumnInfo(name="number")
    var number: Int?,

    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "lower")
    var lower: String?
)