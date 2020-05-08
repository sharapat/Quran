package com.bismillah.quran.ui.translation.ayat

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.core.extentions.inflate

class AyatListAdapter(private val settings: Settings) : RecyclerView.Adapter<AyatListViewHolder>() {

    private var onOptionsClick: (view: View, ayatId: Int) -> Unit = { _, _ ->
        Log.w("Warning", "onOptionsClick function is not set to AyatListAdapter" )
    }
    private var onLinkClick: (Int) -> Unit = { _ ->
        Log.w("Warning", "onLinkClick functions is not set to AyatListAdapter")
    }

    var models: List<Ayat> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun update() {
        notifyDataSetChanged()
    }

    fun setOnOptionsClickListener(onOptionsClickListener: (view: View, ayatId: Int) -> Unit) {
        onOptionsClick = onOptionsClickListener
    }

    fun setOnLinkClickListener(onLinkClickListener: (number: Int) -> Unit) {
        onLinkClick = onLinkClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatListViewHolder {
        val view = parent.inflate(R.layout.item_ayat)
        return AyatListViewHolder(view, onOptionsClick, onLinkClick)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: AyatListViewHolder, position: Int) {
        holder.populateModel(models[position], settings)
    }
}