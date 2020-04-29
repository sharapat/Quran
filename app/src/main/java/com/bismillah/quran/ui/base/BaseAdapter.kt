package com.bismillah.quran.ui.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH: RecyclerView.ViewHolder>(private val itemResId: Int) : RecyclerView.Adapter<VH>() {

    var models: List<T> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun update() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = models.size
}