package com.bismillah.quran.ui.infolist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.callback.InfoItemClickListener
import com.bismillah.quran.data.model.Info
import com.bismillah.quran.extentions.inflate
import kotlinx.android.synthetic.main.item_info_list.view.*

class InfoListAdapter : RecyclerView.Adapter<InfoListAdapter.InfoViewHolder>() {

    inner class InfoViewHolder(itemView: View, private val itemClickListener: InfoItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {

        fun populateModel(info: Info) {
            itemView.tvInfoTitle.text = info.title
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(info.id, info.title)
            }
        }
    }

    var itemClickListener: InfoItemClickListener? = null

    var models: List<Info> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = parent.inflate(R.layout.item_info_list)
        return InfoViewHolder(itemView, itemClickListener)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.populateModel(models[position])
    }
}