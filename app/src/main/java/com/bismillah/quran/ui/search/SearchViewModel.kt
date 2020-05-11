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

    fun searchAyatByWord(word: String) {
        launch {
            searchAyatByWordAsync(word)
        }
    }

    private suspend fun searchAyatByWordAsync(word: String) {
        withContext(Dispatchers.IO) {
            _ayatList.postValue(quranDao.searchAyatByWord("$word%"))
        }
    }
}