package com.bismillah.quran.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.core.SingleLiveEvent
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.data.model.Sure
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _ayatList: SingleLiveEvent<List<Pair<String, Ayat>>> = SingleLiveEvent()
    val ayatList: LiveData<List<Pair<String, Ayat>>>
        get() = _ayatList

    private var _sureToShare: MutableLiveData<Sure> = MutableLiveData()
    val sureToShare: LiveData<Sure>
        get() = _sureToShare

    fun getSureById(sureId: Int) {
        launch { getSureByIdAsync(sureId) }
    }

    private suspend fun getSureByIdAsync(sureId: Int) {
        withContext(Dispatchers.IO) {
            _sureToShare.postValue(quranDao.getSureById(sureId))
        }
    }

    fun searchAyatByWord(word: String) {
        launch {
            searchAyatByWordAsync(word)
        }
    }

    private suspend fun searchAyatByWordAsync(word: String) {
        withContext(Dispatchers.IO) {
            if (word.isEmpty()) {
                _ayatList.postValue(emptyList())
                return@withContext
            }
            val list = quranDao.searchAyatByWord("%$word%")
            val ayatPairList : MutableList<Pair<String, Ayat>> = mutableListOf()
            list.map { ayatPairList.add(Pair(word, it)) }
            _ayatList.postValue(ayatPairList)
        }
    }
}