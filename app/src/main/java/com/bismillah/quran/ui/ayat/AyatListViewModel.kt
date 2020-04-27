package com.bismillah.quran.ui.ayat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.data.model.Sure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AyatListViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var _ayatList: MutableLiveData<List<Ayat>> = MutableLiveData()
    val ayatList: LiveData<List<Ayat>>
        get() = _ayatList

    private var _currentSure: MutableLiveData<Sure> = MutableLiveData()
    val currentSure: LiveData<Sure> = _currentSure

    fun getSureById(sureId: Int) {
        launch { getSureByIdAsync(sureId) }
    }

    fun getAyatList(sureId: Int) {
        launch { getAyatListAsync(sureId) }
    }

    private suspend fun getSureByIdAsync(sureId: Int) {
        withContext(Dispatchers.IO) {
            _currentSure.postValue(quranDao.getSureById(sureId))
        }
    }

    private suspend fun getAyatListAsync(sureId: Int) {
        withContext(Dispatchers.IO) {
            _ayatList.postValue(quranDao.getAllAyatBySureId(sureId))
        }
    }
}