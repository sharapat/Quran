package com.bismillah.quran.ui.sure

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.callback.SureItemClickListener
import com.bismillah.quran.data.model.Sure
import kotlinx.android.synthetic.main.item_sure.view.*

class SureListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun populateModel(sure: Sure, itemClickListener: SureItemClickListener, isOriginalTextNeeded: Boolean) {
        itemView.tvNumber.text = sure.number.toString()
        itemView.tvName.text = sure.name
        itemView.tvDescription.text = "${sure.place} ${sure.description}"
        if (isOriginalTextNeeded)
            itemView.tvOriginalName.text = sure.originalName
        itemView.setOnClickListener {
            itemClickListener.onSureClick(sure.id, sure.name)
        }
        itemView.tvOriginalName.setOnClickListener {
            itemClickListener.onOriginalSureClick(sure.id)
        }
    }
}