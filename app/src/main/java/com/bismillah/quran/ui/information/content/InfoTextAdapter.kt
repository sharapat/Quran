package com.bismillah.quran.ui.information.content

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.InfoText
import com.bismillah.quran.extentions.inflate
import kotlinx.android.synthetic.main.simple_text.view.*

class InfoTextAdapter(private val settings: Settings) : RecyclerView.Adapter<InfoTextAdapter.InfoTextViewHolder>() {

    inner class InfoTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(model: InfoText) {
            itemView.tvText.text = model.text
            itemView.tvText.textSize = settings.getTextSize().toFloat()
        }
    }

    var models: List<InfoText> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoTextViewHolder {
        val view = parent.inflate(R.layout.simple_text)
        return InfoTextViewHolder(view)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: InfoTextViewHolder, position: Int) {
        holder.populateModel(models[position])
    }
}