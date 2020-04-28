package com.bismillah.quran.ui.ayatoriginal

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_explanation_ayat.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class OriginalAyatFragment : BaseFragment(R.layout.fragment_explanation_ayat) {

    private val viewModel : OriginalAyatViewModel by viewModel()
    private val settings: Settings by inject()
    private val safeArgs: OriginalAyatFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideKeyboard(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOriginalAyatListBySureId(safeArgs.sureId)
        viewModel.getSureById(safeArgs.sureId)
        viewModel.originalAyatList.observe(viewLifecycleOwner, Observer {
            var ayatText = ""
            for (ayat in it) {
                ayatText += "${ayat.text}\n\n"
            }
            tvExplanationText.text = ayatText
        })
        viewModel.currentSure.observe(viewLifecycleOwner, Observer {
            tvToolbarTitle.text = it.originalName
        })

        btnMinus.setOnClickListener {
            val currentTextSize = settings.getArabTextSize()
            settings.setArabTextSize(currentTextSize-1)
            tvExplanationText.textSize = (currentTextSize-1).toFloat()
        }

        btnPlus.setOnClickListener {
            val currentTextSize = settings.getArabTextSize()
            settings.setArabTextSize(currentTextSize+1)
            tvExplanationText.textSize = (currentTextSize + 1).toFloat()
        }

        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}