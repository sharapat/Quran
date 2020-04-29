package com.bismillah.quran.callback

interface SureItemClickListener {
    fun onSureClick(sureId: Int, sureName: String)
    fun onOriginalSureClick(sureId: Int)
}