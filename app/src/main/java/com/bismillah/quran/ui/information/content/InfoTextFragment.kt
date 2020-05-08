package com.bismillah.quran.ui.information.content

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bismillah.quran.R
import com.bismillah.quran.data.model.InfoText
import com.bismillah.quran.core.BaseFragment
import com.bismillah.quran.extentions.onClick
import kotlinx.android.synthetic.main.fragment_simple_reading_list.*
import kotlinx.android.synthetic.main.reading_page_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class InfoTextFragment : BaseFragment(R.layout.fragment_simple_reading_list) {
    private val adapter: InfoTextAdapter by inject()
    private val viewModel: InfoViewModel by viewModel()
    private val safeArgs: InfoTextFragmentArgs by navArgs()
    private var infoTitleId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbarTitle.text = safeArgs.titleName
        infoTitleId = safeArgs.infoTitleId
        recyclerView.adapter = adapter
        if (infoTitleId == 11) {
            val info = InfoText(1, 11, getString(R.string.info), "")
            val models: List<InfoText> = listOf(info)
            adapter.models = models
        } else {
            viewModel.getInfoTextsByTitleId(infoTitleId)
        }
        viewModel.infoList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
        backButton.onClick {
            requireActivity().onBackPressed()
        }
        btnMinus.onClick {
            settings.decreaseTextSize()
            adapter.notifyDataSetChanged()
        }
        btnPlus.onClick {
            settings.increaseTextSize()
            adapter.notifyDataSetChanged()
        }
    }
}