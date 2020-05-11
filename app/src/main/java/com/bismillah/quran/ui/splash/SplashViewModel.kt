package com.bismillah.quran.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.Settings
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.data.model.Sure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SplashViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _ayatList: MutableLiveData<List<Ayat>> = MutableLiveData()
    val ayatList: LiveData<List<Ayat>>
        get() = _ayatList

    private val _sureList: MutableLiveData<List<Sure>> = MutableLiveData()
    val sureList: LiveData<List<Sure>>
        get() = _sureList

    fun updateAyatList(ayatList: List<Ayat>, sureList: List<Sure>, doOnFinish: () -> Unit) {
        launch { updateAyatListAsync(ayatList, sureList, doOnFinish) }
    }

    private suspend fun updateAyatListAsync(ayatList: List<Ayat>, sureList: List<Sure>, doOnFinish: () -> Unit) {
        withContext(Dispatchers.IO) {
            ayatList.forEach { ayat ->
                val sureName = sureList.find { sure -> sure.id == ayat.sureId }?.name
                ayat.sureName = sureName
            }
            quranDao.updateAyatList(ayatList)
            doOnFinish.invoke()
        }
    }

    fun getAllAyat() {
        launch { getAllAyatAsync() }
    }

    private suspend fun getAllAyatAsync() {
        withContext(Dispatchers.IO) {
            _ayatList.postValue(quranDao.getAllAyat())
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

}