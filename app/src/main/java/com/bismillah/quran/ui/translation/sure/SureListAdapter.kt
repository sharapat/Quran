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

    private var onSureItemClick: (sureId: Int, sureName: String) -> Unit = { sureId, sureName ->
        Log.w("Warning", "onSureItemClick is not set to SureListAdapter")
    }

    private var onOriginalSureClick: (sureId: Int) -> Unit = {
        Log.w("Warning", "onOriginalSureClick is not set to SureListAdapter")
    }

    fun setOnItemClickListener(onSureItemClick: (sureId: Int, sureName: String) -> Unit) {
        this.onSureItemClick = onSureItemClick
    }

    fun setOnOriginalSureClick(onOriginalSureClick: (sureId: Int) -> Unit) {
        this.onOriginalSureClick = onOriginalSureClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SureListViewHolder {
        val view = parent.inflate(R.layout.item_sure)
        return SureListViewHolder(view, onSureItemClick, onOriginalSureClick)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: SureListViewHolder, position: Int) {
        holder.populateModel(models[position], isOriginalTextNeeded)
    }

}