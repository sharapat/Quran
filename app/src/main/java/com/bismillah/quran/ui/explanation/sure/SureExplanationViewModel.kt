package com.bismillah.quran.ui.explanation.sure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.Explanation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SureExplanationViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _sureExplanationList: MutableLiveData<List<Explanation>> = MutableLiveData()
    val sureExplanationList: LiveData<List<Explanation>>
        get() = _sureExplanationList

    fun getExplanationListBySureId(sureId: Int) {
        launch {
            getExplanationListBySureIdAsync(sureId)
        }
    }

    private suspend fun getExplanationListBySureIdAsync(sureId: Int) {
        withContext(Dispatchers.IO) {
            _sureExplanationList.postValue(quranDao.getExplanationsBySureId(sureId))
        }
    }
}