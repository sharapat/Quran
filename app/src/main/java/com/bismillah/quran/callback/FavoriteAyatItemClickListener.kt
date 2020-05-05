package com.bismillah.quran.callback

import android.view.View

interface FavoriteAyatItemClickListener {
    fun onLinkClick(number: Int)
    fun onItemClick(view: View, ayatId: Int, position: Int)
}