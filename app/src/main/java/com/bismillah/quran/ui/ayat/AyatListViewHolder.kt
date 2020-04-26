package com.bismillah.quran.ui.ayat

import android.os.Build
import android.text.Html
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.data.model.Ayat
import kotlinx.android.synthetic.main.item_ayat.view.*

class AyatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun populateModel(ayat: Ayat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            itemView.tvText.text = Html.fromHtml(ayat.text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            itemView.tvText.text = Html.fromHtml(ayat.text)
        }
    }

    /*
    private fun getSplitPair(text: String) : Pair<String, String> {
        val pointIndex = text.indexOf('.')
        val number = text.substring(0, pointIndex)
        val content = text.substring(pointIndex+2, text.length)
        return Pair(number, content)
    }
    */

}