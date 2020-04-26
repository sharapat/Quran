package com.bismillah.quran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "sureler")
data class Sure (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "number")
    var number: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "place")
    var place: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "lower_name")
    var lowerName: String?,

    @ColumnInfo(name="place_lower")
    var lowerPlace: String?,

    @ColumnInfo(name="lower_description")
    var lowerDescription: String?
)