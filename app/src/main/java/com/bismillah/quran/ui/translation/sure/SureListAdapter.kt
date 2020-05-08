package com.bismillah.quran.ui.translation.sure

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.data.model.Sure
import com.bismillah.quran.core.extentions.inflate

class SureListAdapter(
    private val isOriginalTextNeeded: Boolean = true
) : RecyclerView.Adapter<SureListViewHolder>() {

    var models: List<Sure> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClick: (sureId: Int, sureName: String) -> Unit = { _, _ ->
        Log.w("Warning", "onItemClickListener is not set to SureListAdapter")
    }
    private var onOriginalSureClick: (sureId: Int) -> Unit = { _->
        Log.w("Warning", "onOriginalSureClickListener is not set to SureListAdapter")
    }

    fun setOnItemClickListener(onItemClick: (sureId: Int, sureName: String) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun setOnOriginalSureClickListener(onOriginalSureClick: (sureId: Int) -> Unit) {
        this.onOriginalSureClick = onOriginalSureClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SureListViewHolder {
        val view = parent.inflate(R.layout.item_sure)
        return SureListViewHolder(view, onItemClick, onOriginalSureClick)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: SureListViewHolder, position: Int) {
        holder.populateModel(models[position], isOriginalTextNeeded)
    }

}