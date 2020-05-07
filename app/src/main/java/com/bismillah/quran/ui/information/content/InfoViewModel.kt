package com.bismillah.quran.ui.information.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.InfoText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class InfoViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _infoList: MutableLiveData<List<InfoText>> = MutableLiveData()
    val infoList: LiveData<List<InfoText>>
        get() = _infoList

    fun getInfoTextsByTitleId(id: Int) {
        launch {
            getInfoTextsByTitleIdAsync(id)
        }
    }

    private suspend fun getInfoTextsByTitleIdAsync(id: Int) {
        withContext(Dispatchers.IO) {
            _infoList.postValue(quranDao.getInfoByTitleIf(id))
        }
    }
}