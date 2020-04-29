package com.bismillah.quran.ui.explanation.sure

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ayat_original.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SureExplanationFragment: BaseFragment(R.layout.fragment_ayat_original) {

    private val viewModel: SureExplanationViewModel by viewModel()
    private val settings: Settings by inject()
    private val safeArgs: SureExplanationFragmentArgs by navArgs()
    private val adapter: SureExplanationListAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        tvToolbarTitle.text = safeArgs.sureName
        viewModel.getExplanationListBySureId(safeArgs.sureId)
        viewModel.sureExplanationList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
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