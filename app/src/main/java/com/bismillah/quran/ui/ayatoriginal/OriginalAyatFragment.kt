package com.bismillah.quran.ui.ayatoriginal

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ayat_original.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class OriginalAyatFragment : BaseFragment(R.layout.fragment_ayat_original) {

    private val viewModel : OriginalAyatViewModel by viewModel()
    private val settings: Settings by inject()
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