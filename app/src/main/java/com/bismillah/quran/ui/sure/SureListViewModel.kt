package com.bismillah.quran.ui.sure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.Sure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SureListViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _sureList: MutableLiveData<List<Sure>> = MutableLiveData()
    val sureList: LiveData<List<Sure>> = _sureList

    fun getAllSureTranslations() {
        launch { getAllSureTranslationAsync() }
    }

    private suspend fun getAllSureTranslationAsync() {
        withContext(Dispatchers.IO) {
            _sureList.postValue(quranDao.getAllSure())
        }
    }

    fun searchSureByWord(word: String) {
        launch { searchSureByWordSync(word) }
    }

    private suspend fun searchSureByWordSync(word: String) {
        withContext(Dispatchers.IO) {
            _sureList.postValue(quranDao.searchSureByWord("$word%"))
        }
    }
}