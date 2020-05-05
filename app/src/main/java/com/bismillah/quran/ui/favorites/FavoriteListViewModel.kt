package com.bismillah.quran.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bismillah.quran.data.QuranDao
import com.bismillah.quran.data.model.Ayat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FavoriteListViewModel(private val quranDao: QuranDao) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _favoriteAyatList: MutableLiveData<List<Ayat>> = MutableLiveData()
    val favoriteAyatList: LiveData<List<Ayat>>
        get() = _favoriteAyatList

    fun getFavorites() {
        launch {
            getFavoriteListAsync()
        }
    }

    private suspend fun getFavoriteListAsync() {
        withContext(Dispatchers.IO) {
            _favoriteAyatList.postValue(quranDao.getFavorites())
        }
    }

    fun removeFavorite(ayatId: Int) {
        launch {
            removeFavoriteAsync(ayatId)
        }
    }

    private suspend fun removeFavoriteAsync(ayatId: Int) {
        withContext(Dispatchers.IO) {
            val ayat = quranDao.getAyatById(ayatId)
            ayat.isFavorite = 0
            quranDao.updateAyat(ayat)
        }
    }
}