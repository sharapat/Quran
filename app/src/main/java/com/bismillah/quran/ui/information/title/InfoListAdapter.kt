package com.bismillah.quran.ui.information.title

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.data.model.Info
import com.bismillah.quran.core.extentions.inflate
import com.bismillah.quran.core.extentions.onClick
import kotlinx.android.synthetic.main.item_info_list.view.*

class InfoListAdapter : RecyclerView.Adapter<InfoListAdapter.InfoViewHolder>() {

    inner class InfoViewHolder(itemView: View, private val onItemClick: (id: Int, titleName: String) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun populateModel(info: Info) {
            itemView.tvInfoTitle.text = info.title
            itemView.onClick {
                onItemClick.invoke(info.id, info.title)
            }
        }
    }

    private var onItemClick: (id: Int, titleName: String) -> Unit = { _, _ ->
        Log.w("Warning", "itemClickListener is not set to InfoListAdapter")
    }

    fun setItemClickListener(onItemClick: (id: Int, titleName: String) -> Unit) {
        this.onItemClick = onItemClick
    }

    var models: List<Info> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = parent.inflate(R.layout.item_info_list)
        return InfoViewHolder(itemView, onItemClick)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.populateModel(models[position])
    }
}