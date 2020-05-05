package com.bismillah.quran.ui.explanation.sure

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.Explanation
import com.bismillah.quran.extentions.inflate

class SureExplanationListAdapter(private val settings: Settings) : RecyclerView.Adapter<SureExplanationViewHolder>() {

    var models: List<Explanation> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun update() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SureExplanationViewHolder {
        val view = parent.inflate(R.layout.simple_text)
        return SureExplanationViewHolder(view)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: SureExplanationViewHolder, position: Int) {
        holder.populateModel(models[position], settings)
    }
}