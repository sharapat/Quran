package com.bismillah.quran.ui.translation.sure

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.data.model.Sure
import com.bismillah.quran.core.extentions.onClick
import kotlinx.android.synthetic.main.item_sure.view.*

class SureListViewHolder(
    itemView: View,
    private val onSureItemClick: (sureId: Int, sureName: String) -> Unit,
    private val onOriginalSureClick: (sureId: Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    fun populateModel(sure: Sure, isOriginalTextNeeded: Boolean) {
        itemView.tvNumber.text = sure.number.toString()
        itemView.tvName.text = sure.name
        itemView.tvDescription.text =
            itemView.context.getString(R.string.sure_description, sure.place, sure.description)
        if (isOriginalTextNeeded)
            itemView.tvOriginalName.text = sure.originalName
        itemView.onClick {
            onSureItemClick.invoke(sure.id, sure.name)
        }
        itemView.tvOriginalName.onClick {
            onOriginalSureClick.invoke(sure.id)
        }
    }
}