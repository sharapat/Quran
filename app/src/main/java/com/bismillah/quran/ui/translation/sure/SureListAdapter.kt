package com.bismillah.quran.ui.translation.sure

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.callback.SureItemClickListener
import com.bismillah.quran.data.model.Sure
import com.bismillah.quran.extentions.inflate

class SureListAdapter(private val itemClickListener: SureItemClickListener, private val isOriginalTextNeeded: Boolean = true) : RecyclerView.Adapter<SureListViewHolder>() {

    var models: List<Sure> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SureListViewHolder {
        val view = parent.inflate(R.layout.item_sure)
        return SureListViewHolder(view)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: SureListViewHolder, position: Int) {
        holder.populateModel(models[position], itemClickListener, isOriginalTextNeeded)
    }

}