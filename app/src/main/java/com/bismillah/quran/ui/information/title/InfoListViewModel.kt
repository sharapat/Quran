package com.bismillah.quran.ui.information.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.Info
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class InfoListViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _infoTitleList: MutableLiveData<List<Info>> = MutableLiveData()
    val infoTitleList: LiveData<List<Info>>
        get() = _infoTitleList

    fun getInfoTitles() {
        launch {
            getInfoTitlesAsync()
        }
    }

    private suspend fun getInfoTitlesAsync() {
        withContext(Dispatchers.IO) {
            _infoTitleList.postValue(quranDao.getInfoTitles())
        }
    }
}