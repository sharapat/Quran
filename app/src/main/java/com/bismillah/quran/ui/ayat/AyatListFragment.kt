package com.bismillah.quran.ui.ayat

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideKeyboard(requireActivity())
    }

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
            settings.increaseTextSize()
            adapter.update()
        }
        btnMinus.setOnClickListener {
            settings.decreaseTextSize()
            adapter.update()
        }
    }

    override fun onLinkClick(number: Int) {
        val action = AyatListFragmentDirections.actionAyatListFragment(number, sureName)
        navController.navigate(action)
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