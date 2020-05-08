package com.bismillah.quran.ui.favorites

import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.callback.FavoriteAyatItemClickListener
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.extentions.inflate
import com.bismillah.quran.extentions.onClick
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteListAdapter(private val settings: Settings) : RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {

    var models: MutableList<Ayat> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun itemRemoved(position: Int) {
        models.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, models.size)
    }

    var itemClickListener: FavoriteAyatItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = parent.inflate(R.layout.item_favorite)
        return FavoriteViewHolder(itemView)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.populateModel(models[position], position)
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(model: Ayat, position: Int) {
            itemView.tvSureName.text = model.sureName
            setTextViewHtml(itemView.tvAyatText, model.text)
            itemView.tvSureName.textSize = settings.getTextSize().toFloat()
            itemView.tvAyatText.textSize = settings.getTextSize().toFloat()
            itemView.optionBtn.onClick {
                itemClickListener?.onItemClick(itemView.optionBtn, model.id, position)
            }
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
            val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
            for (span in urls) {
                makeLinkClickable(strBuilder, span)
            }
            tv.text = strBuilder
            tv.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}