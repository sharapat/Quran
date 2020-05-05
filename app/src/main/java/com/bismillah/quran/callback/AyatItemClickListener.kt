package com.bismillah.quran.callback

import android.view.View

interface  AyatItemClickListener {
    fun onLinkClick(number: Int)
    fun onItemClick(view: View, ayatId: Int)
}