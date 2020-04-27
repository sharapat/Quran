package com.bismillah.quran.ui.ayat

import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.Settings
import com.bismillah.quran.callback.AyatItemClickListener
import com.bismillah.quran.data.model.Ayat
import kotlinx.android.synthetic.main.item_ayat.view.*

class AyatListViewHolder(itemView: View, private val itemClickListener: AyatItemClickListener?) : RecyclerView.ViewHolder(itemView) {
    fun populateModel(ayat: Ayat, settings: Settings) {
        itemView.tvText.textSize = settings.getTextSize().toFloat()
        setTextViewHtml(itemView.tvText, ayat.text)
    }

    private fun makeLinkClickable(strBuilder: SpannableStringBuilder, span: URLSpan) {
        val start = strBuilder.getSpanStart(span)
        val end = strBuilder.getSpanEnd(span)
        val flags = strBuilder.getSpanFlags(span)
        val clickable = object: ClickableSpan() {
            override fun onClick(widget: View) {
                val fullText = (widget as TextView).text.toString()
                val link = fullText.substring(start, end)
                itemClickListener?.onLinkClick(link.toInt())
            }
        }
        strBuilder.setSpan(clickable, start, end, flags)
        strBuilder.removeSpan(span)
    }

    private fun setTextViewHtml(tv: TextView, html: String) {
        val sequence: CharSequence = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        val strBuilder = SpannableStringBuilder(sequence)
        val urls = strBuilder.getSpans(0, sequence.length,URLSpan::class.java)
        for (span in urls) {
            makeLinkClickable(strBuilder, span)
        }
        tv.text = strBuilder
        tv.movementMethod = LinkMovementMethod.getInstance()
    }
}