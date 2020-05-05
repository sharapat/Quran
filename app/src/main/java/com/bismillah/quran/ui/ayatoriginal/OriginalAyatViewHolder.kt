package com.bismillah.quran.ui.ayatoriginal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.QuranText
import kotlinx.android.synthetic.main.simple_text.view.*

class OriginalAyatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun populateModel(ayat: QuranText, settings: Settings) {
        itemView.tvText.text = ayat.text
        itemView.tvText.textSize = settings.getArabTextSize().toFloat()
    }
}