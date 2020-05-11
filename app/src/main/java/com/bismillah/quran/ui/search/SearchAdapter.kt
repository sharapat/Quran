package com.bismillah.quran.ui.search

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.core.extentions.inflate
import com.bismillah.quran.core.extentions.onClick
import com.bismillah.quran.core.extentions.setTextViewHtml
import com.bismillah.quran.data.model.Ayat
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(private val settings: Settings) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var models: List<Ayat> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onLinkClick: (String) -> Unit = {
        Log.w("Warning", "onLinkClick is not set to SearchAdapter")
    }

    private var onOptionsClick: (view: View, ayatId: Int) -> Unit = { _, _ ->
        Log.w("Warning", "onOptionsClick function is not set to SearchAdapter" )
    }

    fun setOnLinkClickListener(onLinkClick: (String) -> Unit) {
        this.onLinkClick = onLinkClick
    }

    fun setOnOptionsClickListener(onOptionsClickListener: (view: View, ayatId: Int) -> Unit) {
        onOptionsClick = onOptionsClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = parent.inflate(R.layout.item_search)
        return SearchViewHolder(itemView, onOptionsClick, onLinkClick)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.populateModel(models[position], settings)
    }

    inner class SearchViewHolder(
        itemView: View,
        private val onOptionsClick: (view:View, ayatId: Int) -> Unit,
        private val onLinkClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun populateModel(model: Ayat, settings: Settings) {
            itemView.tvSureName.textSize = settings.getTextSize().toFloat()
            itemView.tvText.textSize = settings.getTextSize().toFloat()
            itemView.tvSureName.text = model.sureName
            itemView.tvText.setTextViewHtml(model.text, onLinkClick)
            itemView.optionBtn.onClick {
                onOptionsClick.invoke(itemView.optionBtn, model.id)
            }
        }

    }
}