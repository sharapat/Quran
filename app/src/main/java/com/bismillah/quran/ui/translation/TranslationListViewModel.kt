package com.bismillah.quran.ui.translation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.Sure
import com.bismillah.quran.repository.TranslationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TranslationListViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _translationList: MutableLiveData<List<Sure>> = MutableLiveData()
    val translationList: LiveData<List<Sure>> = _translationList

    fun getAllSureTranslations() {
        launch { getAllSureTranslationList() }
    }

    private suspend fun getAllSureTranslationList() {
        withContext(Dispatchers.IO) {
            _translationList.postValue(quranDao.getAllSure())
        }
    }
}