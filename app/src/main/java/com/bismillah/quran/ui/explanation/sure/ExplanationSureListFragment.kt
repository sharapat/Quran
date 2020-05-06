package com.bismillah.quran.ui.explanation.sure

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.bismillah.quran.R
import com.bismillah.quran.callback.SureItemClickListener
import com.bismillah.quran.extentions.visibility
import com.bismillah.quran.ui.base.BaseFragment
import com.bismillah.quran.ui.main.MainActivity
import com.bismillah.quran.ui.sure.SureListAdapter
import com.bismillah.quran.ui.sure.SureListViewModel
import kotlinx.android.synthetic.main.fragment_sure_list.*
import kotlinx.android.synthetic.main.main_toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class ExplanationSureListFragment : BaseFragment(R.layout.fragment_sure_list), SureItemClickListener {
    private val viewModel: SureListViewModel by viewModel()
    private lateinit var navController: NavController
    private val adapter = SureListAdapter(this, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard(requireActivity())
        setModeBtnImage()
        navController = Navigation.findNavController(view)
        rvSure.adapter = adapter
        rvSure.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        viewModel.sureList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })

        btnMode.setOnClickListener {
            settings.changeAppMode()
            (requireActivity() as MainActivity).updateThemeAndRecreateActivity()
        }

        btnClearSearchText.setOnClickListener {
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

    override fun onSureClick(sureId: Int, sureName: String) {
        val action = ExplanationSureListFragmentDirections
            .actionExplanationSureListFragmentToSureExplanationFragment(sureId, sureName)
        navController.navigate(action)
    }

    override fun onOriginalSureClick(sureId: Int) {
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