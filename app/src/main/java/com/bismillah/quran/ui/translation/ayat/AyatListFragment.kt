package com.bismillah.quran.ui.translation.ayat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.bismillah.quran.R
import com.bismillah.quran.core.BaseFragment
import com.bismillah.quran.extentions.addVertDivider
import com.bismillah.quran.extentions.onClick
import kotlinx.android.synthetic.main.fragment_ayat_list.*
import kotlinx.android.synthetic.main.reading_page_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class AyatListFragment : BaseFragment(R.layout.fragment_ayat_list) {

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
        adapter.setOnLinkClickListener {
            onLinkClick(it)
        }
        adapter.setOnOptionsClickListener { v, ayatId ->
            onOptionsBtnClick(v, ayatId)
        }
        rvAyat.adapter = adapter
        rvAyat.addVertDivider(context)
        val sureId = safeArgs.sureId
        viewModel.getAyatList(sureId)
        viewModel.getSureById(sureId)
        viewModel.selectedAyat.observe(viewLifecycleOwner, Observer { ayat->
            goToShare(ayat.text)
        })
        viewModel.currentSure.observe(viewLifecycleOwner, Observer {
            tvToolbarTitle.text = it.name
            sureName = it.name
        })
        viewModel.ayatList.observe(viewLifecycleOwner, Observer {
            adapter.models = it
        })
        backButton.onClick {
            activity?.onBackPressed()
        }
        btnPlus.onClick {
            settings.increaseTextSize()
            adapter.update()
        }
        btnMinus.onClick {
            settings.decreaseTextSize()
            adapter.update()
        }
    }

    private fun onLinkClick(number: Int) {
        val action = AyatListFragmentDirections.actionAyatListFragment(number, sureName)
        navController.navigate(action)
    }

    private fun onOptionsBtnClick(view: View, ayatId: Int) {
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
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getAyatTextWithoutNumber(ayatText))
        startActivity(Intent.createChooser(intent, resources.getString(R.string.share_ayat)))
    }

    private fun getShareSubjectText(ayatNumber: Int): String {
        if (ayatNumber == 0) return ""
        val sure = viewModel.currentSure.value
        return "${getString(R.string.app_name)}\n${sure?.number} - ${sure?.name}, $ayatNumber-аят:"
    }

    private fun getAyatTextWithoutNumber(ayatText: String) : String {
        val subject = getShareSubjectText(getNumberFromAyatText(ayatText))
        val number = ayatText.substring(0, ayatText.indexOf('.'))
        var result = if (number.isDigitsOnly()) {
            ayatText.substring(ayatText.indexOf('.') + 2, ayatText.length)
        } else ayatText
        while(result.indexOf("<sup>") >= 0) {
            result = result.removeRange(result.indexOf("<sup>"), result.indexOf("</sup>")+6)
        }
        return "$subject\n\n$result"
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