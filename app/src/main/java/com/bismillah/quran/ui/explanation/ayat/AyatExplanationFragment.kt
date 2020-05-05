package com.bismillah.quran.ui.explanation.ayat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.ui.base.BaseFragment
import com.bismillah.quran.ui.explanation.sure.SureExplanationListAdapter
import kotlinx.android.synthetic.main.fragment_simple_reading_list.*
import kotlinx.android.synthetic.main.reading_page_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AyatExplanationFragment : BaseFragment(R.layout.fragment_simple_reading_list) {

    private val viewModel: AyatExplanationViewModel by viewModel()
    private val safeArgs: AyatExplanationFragmentArgs by navArgs()
    private val adapter: SureExplanationListAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        viewModel.getExplanationsByNumber(safeArgs.explanationNumber)
        viewModel.explanationList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
        tvToolbarTitle.text = safeArgs.sureName
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        btnMinus.setOnClickListener {
            settings.decreaseTextSize()
            adapter.update()
        }
        btnPlus.setOnClickListener {
            settings.increaseTextSize()
            adapter.update()
        }
    }
}