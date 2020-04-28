package com.bismillah.quran.ui.explanation.ayat

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

class AyatExplanationViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var _explanationList: MutableLiveData<List<Explanation>> = MutableLiveData()
    val explanationList: LiveData<List<Explanation>>
        get() = _explanationList

    fun getExplanationsByNumber(number: Int) {
        launch { getExplanationsByNumberAsync(number) }
    }

    private suspend fun getExplanationsByNumberAsync(number: Int) {
        withContext(Dispatchers.IO) {
            _explanationList.postValue(quranDao.getExplanationsByNumber(number))
        }
    }

}