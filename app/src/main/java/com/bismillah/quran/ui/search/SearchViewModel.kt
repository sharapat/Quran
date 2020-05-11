package com.bismillah.quran.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.data.model.Sure
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _ayatList: MutableLiveData<List<Ayat>> = MutableLiveData()
    val ayatList: LiveData<List<Ayat>>
        get() = _ayatList

    private val _sureList: MutableLiveData<List<Sure>> = MutableLiveData()
    val sureList: LiveData<List<Sure>>
        get() = _sureList

    fun searchAyatByWord(word: String) {
        launch {
            searchAyatByWordAsync(word)
        }
    }

    fun getAllSure() {
        launch { getAllSureAsync() }
    }

    private suspend fun getAllSureAsync() {
        withContext(Dispatchers.IO) {
            _sureList.postValue(quranDao.getAllSure())
        }
    }

    private suspend fun searchAyatByWordAsync(word: String) {
        withContext(Dispatchers.IO) {
            _ayatList.postValue(quranDao.searchAyatByWord("$word%"))
        }
    }
}