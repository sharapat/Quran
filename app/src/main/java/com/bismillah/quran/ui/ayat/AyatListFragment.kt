package com.bismillah.quran.ui.ayat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.bismillah.quran.R
import com.bismillah.quran.callback.AyatItemClickListener
import com.bismillah.quran.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ayat_list.*
import kotlinx.android.synthetic.main.reading_page_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class AyatListFragment : BaseFragment(R.layout.fragment_ayat_list), AyatItemClickListener {

    private val viewModel: AyatListViewModel by viewModel()
    private val adapter: AyatListAdapter by inject()
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
        viewModel.selectedAyat.observe(viewLifecycleOwner, Observer { ayat->
            goToShare(ayat.text)
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

    override fun onItemClick(view: View, ayatId: Int) {
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
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.item_favorite -> {
                    viewModel.setFavorite(ayatId)
                    toastSH(getString(R.string.ayat_has_been_added_to_favorites))
                    return@setOnMenuItemClickListener true
                }
                R.id.item_share -> {
                    viewModel.getSelectedAyat(ayatId)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }

    private fun goToShare(ayatText: String) {
        val ayatNumber = getNumberFromAyatText(ayatText)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getShareSubjectText(ayatNumber))
        intent.putExtra(Intent.EXTRA_TEXT, getAyatTextWithoutNumber(ayatText))
        startActivity(Intent.createChooser(intent, resources.getString(R.string.share_ayat)))
    }

    private fun getShareSubjectText(ayatNumber: Int): String {
        if (ayatNumber == 0) return ""
        val sure = viewModel.currentSure.value
        return "${sure?.number} - ${sure?.name}, $ayatNumber-аят"
    }

    private fun getAyatTextWithoutNumber(ayatText: String) : String {
        val number = ayatText.substring(0, ayatText.indexOf('.'))
        var result = if (number.isDigitsOnly()) {
            ayatText.substring(ayatText.indexOf('.') + 2, ayatText.length)
        } else ayatText
        while(result.indexOf("<sup>") >= 0) {
            result = result.removeRange(result.indexOf("<sup>"), result.indexOf("</sup>")+6)
        }
        return result
    }

    private fun getNumberFromAyatText(ayatText: String) : Int {
        if (ayatText.indexOf('.') < 0) return 0
        val number = ayatText.substring(0, ayatText.indexOf('.'))
        return if (number.isDigitsOnly()) {
            number.toInt()
        } else {
            0
        }
    }
}