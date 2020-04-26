package com.bismillah.quran.ui.translation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.data.model.Sure
import kotlinx.android.synthetic.main.item_sure.view.*

class SureListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun populateModel(sure: Sure) {
        itemView.tvNumber.text = sure.number.toString()
        itemView.tvName.text = sure.name
        itemView.tvDescription.text = "${sure.place} ${sure.description}"
    }
}