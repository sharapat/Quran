package com.bismillah.quran.ui.explanation.sure

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.Explanation
import kotlinx.android.synthetic.main.simple_text.view.*

class SureExplanationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun populateModel(explanation: Explanation, settings: Settings) {
        itemView.tvText.text = explanation.text
        itemView.tvText.textSize = settings.getTextSize().toFloat()
    }
}