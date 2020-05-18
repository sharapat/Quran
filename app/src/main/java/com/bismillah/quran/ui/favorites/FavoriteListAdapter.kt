package com.bismillah.quran.ui.favorites

import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.core.extentions.inflate
import com.bismillah.quran.core.extentions.onClick
import com.bismillah.quran.core.extentions.setTextViewHtml
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

    private var onItemClick: (view: View, ayatId: Int, position: Int) -> Unit = { _, _, _ ->
        Log.w("Warning", "onItemClick is not set to FavoriteListAdapter")
    }
    private var onLinkClick: (String) -> Unit = {
        Log.w("Warning", "onLinkClick is not set to FavoriteListAdapter")
    }

    fun setOnItemClickListener(onItemClick: (view: View, ayatId: Int, position: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun setOnLinkClickListener(onLinkClick: (String) -> Unit) {
        this.onLinkClick = onLinkClick
    }

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
            itemView.tvAyatText.setTextViewHtml(model.text, onLinkClick)
            itemView.tvSureName.textSize = settings.getTextSize().toFloat()
            itemView.tvAyatText.textSize = settings.getTextSize().toFloat()
            itemView.optionBtn.onClick {
                onItemClick.invoke(itemView.optionBtn, model.id, position)
            }
        }
    }
}