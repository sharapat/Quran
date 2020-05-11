package com.bismillah.quran.ui.translation.ayat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.core.extentions.onClick
import com.bismillah.quran.core.extentions.setTextViewHtml
import kotlinx.android.synthetic.main.item_ayat.view.*

class AyatListViewHolder(
    itemView: View,
    private val onOptionsClick: (view:View, ayatId: Int) -> Unit,
    private val onLinkClick: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    fun populateModel(ayat: Ayat, settings: Settings) {
        itemView.tvText.textSize = settings.getTextSize().toFloat()
        itemView.tvText.setTextViewHtml(ayat.text, onLinkClick)
        itemView.optionBtn.onClick {
            onOptionsClick.invoke(itemView.optionBtn, ayat.id)
        }
    }
}