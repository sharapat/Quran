package com.bismillah.quran.ui.ayat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.Ayat

class AyatListAdapter(private val settings: Settings) : RecyclerView.Adapter<AyatListViewHolder>() {

    var models: List<Ayat> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun update() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ayat, parent, false)
        return AyatListViewHolder(view)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: AyatListViewHolder, position: Int) {
        holder.populateModel(models[position], settings)
    }
}