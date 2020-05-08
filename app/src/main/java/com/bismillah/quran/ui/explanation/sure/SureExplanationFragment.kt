package com.bismillah.quran.ui.explanation.sure

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bismillah.quran.R
import com.bismillah.quran.core.BaseFragment
import com.bismillah.quran.core.extentions.onClick
import kotlinx.android.synthetic.main.fragment_simple_reading_list.*
import kotlinx.android.synthetic.main.reading_page_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SureExplanationFragment: BaseFragment(R.layout.fragment_simple_reading_list) {

    private val viewModel: SureExplanationViewModel by viewModel()
    private val safeArgs: SureExplanationFragmentArgs by navArgs()
    private val adapter: SureExplanationListAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard(requireActivity())
        recyclerView.adapter = adapter
        tvToolbarTitle.text = safeArgs.sureName
        viewModel.getExplanationListBySureId(safeArgs.sureId)
        viewModel.sureExplanationList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
        backButton.onClick {
            requireActivity().onBackPressed()
        }
        btnMinus.onClick {
            settings.decreaseTextSize()
            adapter.update()
        }
        btnPlus.onClick {
            settings.increaseTextSize()
            adapter.update()
        }
    }

}