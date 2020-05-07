package com.bismillah.quran.ui.favorites

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.bismillah.quran.R
import com.bismillah.quran.callback.FavoriteAyatItemClickListener
import com.bismillah.quran.extentions.visibility
import com.bismillah.quran.core.BaseFragment
import com.bismillah.quran.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.main_toolbar.*
import org.koin.android.ext.android.inject
import java.lang.Exception

class FavoriteListFragment : BaseFragment(R.layout.fragment_favorites), FavoriteAyatItemClickListener {
    private val adapter: FavoriteListAdapter by inject()
    private val viewModel: FavoriteListViewModel by inject()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideKeyboard(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setModeBtnImage()
        navController = Navigation.findNavController(view)
        adapter.itemClickListener = this
        rvFavorites.adapter = adapter
        rvFavorites.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel.getFavorites()
        tv_toolbar_title.text = getText(R.string.favorites)
        viewModel.favoriteAyatList.observe(viewLifecycleOwner, Observer {
            emptyFavorites.visibility(it.isNullOrEmpty())
            adapter.models = it.toMutableList()
        })
        btnMode.setOnClickListener {
            settings.changeAppMode()
            (requireActivity() as MainActivity).updateThemeAndRecreateActivity()
        }
    }

    override fun onLinkClick(number: Int) {
        val action = FavoriteListFragmentDirections.actionFavoriteListFragmentToAyatExplanationFragment(number, getString(R.string.favorites))
        navController.navigate(action)
    }

    override fun onItemClick(view: View, ayatId: Int, position: Int) {
        val popupMenu = PopupMenu(context, view)
        try {
            val field = popupMenu.javaClass.getDeclaredField("mPopup")
            field.isAccessible = true
            val menuPopupHelper = field.get(popupMenu)
            val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
            val setForceIcons = classPopupHelper.getMethod("setForceShowIcon", Boolean::class.java)
            setForceIcons.invoke(menuPopupHelper, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_ayat_item, popupMenu.menu)
        popupMenu.menu.getItem(1).title = getText(R.string.removeFavorite)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.item_favorite -> {
                    viewModel.removeFavorite(ayatId)
                    adapter.itemRemoved(position)
                    emptyFavorites.visibility(adapter.models.isNullOrEmpty())
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }

    private fun setModeBtnImage() {
        if (settings.isAppDarkMode()) {
            btnMode.setImageResource(R.drawable.sun)
        } else {
            btnMode.setImageResource(R.drawable.moon)
        }
    }

}