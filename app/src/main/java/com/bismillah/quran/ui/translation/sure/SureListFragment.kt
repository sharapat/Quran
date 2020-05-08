package com.bismillah.quran.ui.translation.sure

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bismillah.quran.R
import com.bismillah.quran.core.extentions.visibility
import com.bismillah.quran.core.BaseFragment
import com.bismillah.quran.core.extentions.addVertDivider
import com.bismillah.quran.core.extentions.onClick
import com.bismillah.quran.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_sure_list.*
import kotlinx.android.synthetic.main.main_toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class SureListFragment : BaseFragment(R.layout.fragment_sure_list) {

    private val viewModel: SureListViewModel by viewModel()
    private val adapter: SureListAdapter = SureListAdapter(true)
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setModeBtnImage()
        navController = Navigation.findNavController(view)
        adapter.setOnItemClickListener(onSureItemClick)
        adapter.setOnOriginalSureClickListener(onOriginalSureClick)
        rvSure.adapter = adapter
        rvSure.addVertDivider(context)

        viewModel.sureList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })

        btnMode.onClick {
            settings.changeAppMode()
            (requireActivity() as MainActivity).updateThemeAndRecreateActivity()
        }

        btnClearSearchText.onClick {
            etSearch.text.clear()
        }

        etSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                fillRecyclerView(s)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun setModeBtnImage() {
        if (settings.isAppDarkMode()) {
            btnMode.setImageResource(R.drawable.sun)
        } else {
            btnMode.setImageResource(R.drawable.moon)
        }
    }

    override fun onStart() {
        super.onStart()
        fillRecyclerView(etSearch.text)
    }

    private val onSureItemClick = { sureId: Int, sureName: String ->
        val action = SureListFragmentDirections.actionTranslationFragmentToAyatListFragment(sureId, sureName)
        navController.navigate(action)
    }

    private val onOriginalSureClick = { sureId: Int ->
        val action = SureListFragmentDirections.actionTranslationFragmentToOriginalAyatFragment(sureId)
        navController.navigate(action)
    }

    fun fillRecyclerView(s: Editable?) {
        if (s.isNullOrEmpty()) {
            btnClearSearchText.visibility(false)
            viewModel.getAllSureTranslations()
        }
        else {
            btnClearSearchText.visibility(true)
            viewModel.searchSureByWord(s.toString())
        }
    }
}