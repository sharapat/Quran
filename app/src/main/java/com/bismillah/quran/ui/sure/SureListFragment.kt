package com.bismillah.quran.ui.sure

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.bismillah.quran.R
import com.bismillah.quran.callback.SureItemClickListener
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sure_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class SureListFragment : BaseFragment(R.layout.fragment_sure_list), SureItemClickListener {

    private val viewModel: SureListViewModel by viewModel()
    private val adapter: SureListAdapter = SureListAdapter(this)
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        rvSure.adapter = adapter
        rvSure.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel.getAllSureTranslations()
        viewModel.translationList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
    }

    override fun onSureClick(sureId: Int) {
        val action = SureListFragmentDirections.actionTranslationFragmentToAyatListFragment(sureId)
        navController.navigate(action)
    }
}