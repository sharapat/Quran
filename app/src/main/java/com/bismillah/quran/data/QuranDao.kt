package com.bismillah.quran.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.bismillah.quran.data.model.*

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

    @Query("SELECT * FROM tusindirmeler WHERE sure=:sureId")
    fun getExplanationsBySureId(sureId: Int): List<Explanation>

    @Query("SELECT * FROM texts WHERE title=:sureId")
    fun getOriginalAyatListBySureId(sureId: Int): List<QuranText>

    @Query("SELECT * FROM ayatlar WHERE is_favorite = 1")
    fun getFavorites(): List<Ayat>

    @Update
    fun updateAyat(ayat: Ayat)

    @Query("SELECT * FROM ayatlar WHERE id=:ayatId")
    fun getAyatById(ayatId: Int): Ayat

    @Query("SELECT * FROM qosimsha")
    fun getInfoTitles(): List<Info>

    @Query("SELECT * FROM qosimsha_text WHERE qosimsha=:infoTitleId")
    fun getInfoByTitleIf(infoTitleId: Int) : List<InfoText>
}