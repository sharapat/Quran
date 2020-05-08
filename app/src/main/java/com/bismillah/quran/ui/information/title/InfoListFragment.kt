package com.bismillah.quran.ui.information.title

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.bismillah.quran.R
import com.bismillah.quran.callback.InfoItemClickListener
import com.bismillah.quran.core.BaseFragment
import com.bismillah.quran.extentions.onClick
import com.bismillah.quran.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.main_toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class InfoListFragment : BaseFragment(R.layout.fragment_info), InfoItemClickListener {
    private val adapter = InfoListAdapter()
    private val viewModel: InfoListViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setModeBtnImage()
        hideKeyboard(requireActivity())
        adapter.itemClickListener = this
        rvInfo.adapter = adapter
        rvInfo.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel.getInfoTitles()
        viewModel.infoTitleList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
        btnMode.onClick {
            settings.changeAppMode()
            (requireActivity() as MainActivity).updateThemeAndRecreateActivity()
        }
    }

    override fun onItemClick(id: Int, titleName: String) {
        val action = InfoListFragmentDirections.actionInfoListFragmentToInfoTextFragment(id, titleName)
        navController.navigate(action)
    }

    private fun setModeBtnImage() {
        if (settings.isAppDarkMode()) {
            btnMode.setImageResource(R.drawable.sun)
        } else {
            btnMode.setImageResource(R.drawable.moon)
        }
    }
}