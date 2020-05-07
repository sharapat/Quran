package com.bismillah.quran.ui.translation.ayat

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.callback.AyatItemClickListener
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.extentions.inflate

class AyatListAdapter(private val settings: Settings) : RecyclerView.Adapter<AyatListViewHolder>() {

    var itemClickListener: AyatItemClickListener? = null

    var models: List<Ayat> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun update() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatListViewHolder {
        val view = parent.inflate(R.layout.item_ayat)
        return AyatListViewHolder(view, itemClickListener)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: AyatListViewHolder, position: Int) {
        holder.populateModel(models[position], settings)
    }
}