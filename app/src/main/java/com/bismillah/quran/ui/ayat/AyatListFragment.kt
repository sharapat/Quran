package com.bismillah.quran.ui.ayat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.di.dataModule
import com.bismillah.quran.extentions.dp
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ayat_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AyatListFragment : BaseFragment(R.layout.fragment_ayat_list) {

    private val viewModel: AyatListViewModel by viewModel()
    private val adapter: AyatListAdapter by inject()
    private val settings: Settings by inject()
    private val safeArgs: AyatListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAyat.adapter = adapter
        rvAyat.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val sureId = safeArgs.sureId
        viewModel.getAyatList(sureId)
        viewModel.getSureById(sureId)
        viewModel.currentSure.observe(viewLifecycleOwner, Observer {
            tvToolbarTitle.text = it.name
        })
        viewModel.ayatList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        btnPlus.setOnClickListener {
            val currentSize = settings.getTextSize()
            settings.setTextSize(currentSize+2)
            adapter.update()
        }
        btnMinus.setOnClickListener {
            val currentSize = settings.getTextSize()
            settings.setTextSize(currentSize-2)
            adapter.update()
        }
    }

}