package com.bismillah.quran.ui.ayatoriginal

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.QuranText
import com.bismillah.quran.extentions.inflate

class OriginalAyatRVAdapter(private val settings: Settings) : RecyclerView.Adapter<OriginalAyatViewHolder>() {

    var models: List<QuranText> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun update() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OriginalAyatViewHolder {
        val view = parent.inflate(R.layout.simple_text)
        return OriginalAyatViewHolder(view)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: OriginalAyatViewHolder, position: Int) {
        holder.populateModel(models[position], settings)
    }
}