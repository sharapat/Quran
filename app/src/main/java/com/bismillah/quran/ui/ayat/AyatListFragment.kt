package com.bismillah.quran.ui.ayat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.callback.AyatItemClickListener
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ayat_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AyatListFragment : BaseFragment(R.layout.fragment_ayat_list), AyatItemClickListener {

    private val viewModel: AyatListViewModel by viewModel()
    private val adapter: AyatListAdapter by inject()
    private val settings: Settings by inject()
    private val safeArgs: AyatListFragmentArgs by navArgs()
    private lateinit var navController: NavController
    private lateinit var sureName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        adapter.itemClickListener = this
        rvAyat.adapter = adapter
        rvAyat.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val sureId = safeArgs.sureId
        viewModel.getAyatList(sureId)
        viewModel.getSureById(sureId)
        viewModel.currentSure.observe(viewLifecycleOwner, Observer {
            tvToolbarTitle.text = it.name
            sureName = it.name
        })
        viewModel.ayatList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        btnPlus.setOnClickListener {
            val currentSize = settings.getTextSize()
            settings.setTextSize(currentSize + 2)
            adapter.update()
        }
        btnMinus.setOnClickListener {
            val currentSize = settings.getTextSize()
            settings.setTextSize(currentSize - 2)
            adapter.update()
        }
    }

    override fun onLinkClick(number: Int) {
        val action = AyatListFragmentDirections.actionAyatListFragment(number, sureName)
        navController.navigate(action)
    }

}