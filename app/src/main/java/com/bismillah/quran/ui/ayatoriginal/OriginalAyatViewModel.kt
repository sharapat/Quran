package com.bismillah.quran.ui.ayatoriginal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.QuranText
import com.bismillah.quran.data.model.Sure
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OriginalAyatViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var _originalAyatList: MutableLiveData<List<QuranText>> = MutableLiveData()
    val originalAyatList: LiveData<List<QuranText>>
        get() = _originalAyatList

    private var _currentSure: MutableLiveData<Sure> = MutableLiveData()
    val currentSure: LiveData<Sure> = _currentSure

    fun getOriginalAyatListBySureId(sureId: Int) {
        launch {
            getOriginalAyatListBySureIdAsync(sureId)
        }
    }

    fun getSureById(sureId: Int) {
        launch { getSureByIdAsync(sureId) }
    }

    private suspend fun getOriginalAyatListBySureIdAsync(sureId: Int) {
        withContext(Dispatchers.IO) {
            _originalAyatList.postValue(quranDao.getOriginalAyatListBySureId(sureId))
        }
    }

    private suspend fun getSureByIdAsync(sureId: Int) {
        withContext(Dispatchers.IO) {
            _currentSure.postValue(quranDao.getSureById(sureId))
        }
    }
}