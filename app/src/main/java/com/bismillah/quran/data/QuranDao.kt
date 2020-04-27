package com.bismillah.quran.data

import androidx.room.Dao
import androidx.room.Query
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.data.model.Explanation
import com.bismillah.quran.data.model.Sure

@Dao
interface QuranDao {

    @Query("SELECT * FROM sureler")
    fun getAllSure() : List<Sure>

    @Query("SELECT * FROM sureler WHERE name like :word")
    fun searchSureByWord(word: String) : List<Sure>

    @Query("SELECT * FROM sureler WHERE id=:sureId")
    fun getSureById(sureId: Int) : Sure

    @Query("SELECT * FROM ayatlar WHERE sure=:sureId")
    fun getAllAyatBySureId(sureId: Int): List<Ayat>

    @Query("SELECT * FROM tusindirmeler WHERE number=:number")
    fun getExplanationsByNumber(number: Int): List<Explanation>

}