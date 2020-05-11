package com.bismillah.quran.ui.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.bismillah.quran.R
import com.bismillah.quran.core.BaseFragment
import com.bismillah.quran.data.model.Sure
import kotlinx.android.synthetic.main.layout_recycler.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModel()
    private val adapter: SearchAdapter by viewModel()
    private lateinit var navController: NavController
    private var sureList: List<Sure> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideKeyboard(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        recyclerView.adapter = adapter
        viewModel.getAllSure()
        viewModel.sureList.observe(viewLifecycleOwner, Observer {
            sureList = it
        })
        viewModel.ayatList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
    }
}