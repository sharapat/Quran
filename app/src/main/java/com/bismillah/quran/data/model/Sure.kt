package com.bismillah.quran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "sureler")
data class Sure (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "number")
    val number: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "place")
    val place: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "lower_name")
    val lowerName: String,

    @ColumnInfo(name="place_lower")
    val lowerPlace: String,

    @ColumnInfo(name="lower_description")
    val lowerDescription: String
)