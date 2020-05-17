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

    private val _ayatList: MutableLiveData<List<Pair<String, Ayat>>> = MutableLiveData()
    val ayatList: LiveData<List<Pair<String, Ayat>>>
        get() = _ayatList

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