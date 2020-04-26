package com.bismillah.quran.data

import androidx.room.Dao
import androidx.room.Query
import com.bismillah.quran.data.model.Sure

@Dao
interface QuranDao {

    @Query("SELECT * FROM sureler")
    fun getAllSure() : List<Sure>
}