package com.bismillah.quran.ui.explanation.ayat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ayat_list.*
import kotlinx.android.synthetic.main.fragment_explanation_ayat.*
import kotlinx.android.synthetic.main.fragment_explanation_ayat.backButton
import kotlinx.android.synthetic.main.fragment_explanation_ayat.btnMinus
import kotlinx.android.synthetic.main.fragment_explanation_ayat.btnPlus
import kotlinx.android.synthetic.main.fragment_explanation_ayat.tvToolbarTitle
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AyatExplanationFragment : BaseFragment(R.layout.fragment_explanation_ayat) {

    private val viewModel: AyatExplanationViewModel by viewModel()
    private val safeArgs: AyatExplanationFragmentArgs by navArgs()
    private val settings: Settings by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getExplanationsByNumber(safeArgs.explanationNumber)
        viewModel.explanationList.observe(viewLifecycleOwner, Observer {
            var text = ""
            for (explanation in it) {
                text += "${explanation.text}\n\n"
            }
            tvExplanationText.text = text
        })
        tvToolbarTitle.text = safeArgs.sureName
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        btnMinus.setOnClickListener {
            val currentTextSize = settings.getTextSize()
            settings.setTextSize(currentTextSize-1)
            tvExplanationText.textSize = (currentTextSize-1).toFloat()
        }
        btnPlus.setOnClickListener {
            val currentTextSize = settings.getTextSize()
            settings.setTextSize(currentTextSize+1)
            tvExplanationText.textSize = (currentTextSize+1).toFloat()
        }
    }
}