package com.bismillah.quran.ui.translation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.bismillah.quran.R
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_translation.*
import org.koin.android.viewmodel.ext.android.viewModel

class TranslationListFragment : BaseFragment(R.layout.fragment_translation) {

    private val viewModel: TranslationListViewModel by viewModel()
    private val adapter: SureListAdapter = SureListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSure.adapter = adapter
        rvSure.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel.getAllSureTranslations()
        viewModel.translationList.observe(viewLifecycleOwner, Observer {
            Log.d("modeller", it.toString())
            adapter.models = it
        })
    }
}