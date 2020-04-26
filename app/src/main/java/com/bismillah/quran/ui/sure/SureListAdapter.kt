package com.bismillah.quran.ui.sure

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.callback.SureItemClickListener
import com.bismillah.quran.data.model.Sure

class SureListAdapter(private val itemClickListener: SureItemClickListener) : RecyclerView.Adapter<SureListViewHolder>() {

    var models: List<Sure> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SureListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sure, parent, false)
        return SureListViewHolder(view)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: SureListViewHolder, position: Int) {
        holder.populateModel(models[position], itemClickListener)
    }

}