package com.bismillah.quran.ui.ayatoriginal

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bismillah.quran.R
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_simple_reading_list.*
import kotlinx.android.synthetic.main.reading_page_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class OriginalAyatFragment : BaseFragment(R.layout.fragment_simple_reading_list) {

    private val viewModel : OriginalAyatViewModel by viewModel()
    private val safeArgs: OriginalAyatFragmentArgs by navArgs()
    private val adapter: OriginalAyatRVAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideKeyboard(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        viewModel.getOriginalAyatListBySureId(safeArgs.sureId)
        viewModel.getSureById(safeArgs.sureId)
        viewModel.originalAyatList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
        viewModel.currentSure.observe(viewLifecycleOwner, Observer {
            tvToolbarTitle.text = it.originalName
        })

        btnMinus.setOnClickListener {
            settings.decreaseArabTextSize()
            adapter.update()
        }

        btnPlus.setOnClickListener {
            settings.increaseArabTextSize()
            adapter.update()
        }

        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}