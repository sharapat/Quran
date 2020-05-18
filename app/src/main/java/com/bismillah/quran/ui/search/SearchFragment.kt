package com.bismillah.quran.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.text.HtmlCompat
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bismillah.quran.R
import com.bismillah.quran.core.BaseFragment
import com.bismillah.quran.core.extentions.addVertDivider
import com.bismillah.quran.core.extentions.ifContainsLatin
import com.bismillah.quran.core.extentions.onClick
import com.bismillah.quran.core.extentions.visibility
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.ui.main.MainActivity
import com.bismillah.quran.ui.translation.ayat.AyatListViewModel
import kotlinx.android.synthetic.main.layout_recycler.*
import kotlinx.android.synthetic.main.main_toolbar.*
import kotlinx.android.synthetic.main.search_action.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModel()
    private val ayatViewModel: AyatListViewModel by viewModel()
    private val adapter: SearchAdapter by inject()
    private lateinit var selectedAyat: Ayat
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideKeyboard(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etSearch.hint = getString(R.string.seach_by_ayat)
        if (etSearch.text.isNullOrEmpty()) {
            adapter.models = emptyList()
        }
        navController = Navigation.findNavController(view)
        adapter.setOnLinkClickListener(onLinkClick)
        adapter.setOnOptionsClickListener(onOptionsBtnClick)
        recyclerView.adapter = adapter
        recyclerView.addVertDivider(requireContext())
        viewModel.ayatList.observe(viewLifecycleOwner, Observer {
            if (etSearch.text.isNotEmpty()) {
                adapter.models = it
            } else {
                adapter.models = emptyList()
            }
        })
        viewModel.sureToShare.observe(viewLifecycleOwner, Observer {
            ayatViewModel.getSelectedAyat(selectedAyat.id)
        })
        ayatViewModel.selectedAyat.observe(viewLifecycleOwner, Observer { ayat ->
            goToShare(ayat.text)
        })
        etSearch.addTextChangedListener {
            if (it.toString().ifContainsLatin) {
                btnClearSearchText.visibility(false)
                etSearch.error = HtmlCompat.fromHtml(
                    "<font color=\"#ffffff\">${getString(R.string.you_can_not_use_latin)}<font>",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                return@addTextChangedListener
            }
            if (it.toString().isNotEmpty()) {
                btnClearSearchText.visibility(true)
            } else {
                btnClearSearchText.visibility(false)
            }
            viewModel.searchAyatByWord(it.toString())
        }
        btnMode.onClick {
            settings.changeAppMode()
            (requireActivity() as MainActivity).updateThemeAndRecreateActivity()
        }
        btnClearSearchText.onClick {
            etSearch.text.clear()
        }
        setModeBtnImage()
    }

    private fun setModeBtnImage() {
        if (settings.isAppDarkMode()) {
            btnMode.setImageResource(R.drawable.sun)
        } else {
            btnMode.setImageResource(R.drawable.moon)
        }
    }

    private val onLinkClick = { number: String ->
        val action = SearchFragmentDirections.actionSearchFragmentToAyatExplanationFragment(
            number.toInt(),
            getString(R.string.explanation)
        )
        navController.navigate(action)
    }

    private val onOptionsBtnClick = { view: View, ayat: Ayat ->
        val popupMenu = PopupMenu(context, view)
        selectedAyat = ayat
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
            when (it.itemId) {
                R.id.item_favorite -> {
                    ayatViewModel.setFavorite(ayat.id)
                    toastSH(getString(R.string.ayat_has_been_added_to_favorites))
                    return@setOnMenuItemClickListener true
                }
                R.id.item_share -> {
                    viewModel.getSureById(ayat.sureId)
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
        val sure = viewModel.sureToShare.value
        return "${getString(R.string.app_name)}\n${sure?.number} - ${sure?.name}, $ayatNumber-аят:"
    }

    private fun getAyatTextWithoutNumber(ayatText: String): String {
        val subject = getShareSubjectText(getNumberFromAyatText(ayatText))
        val number = ayatText.substring(0, ayatText.indexOf('.'))
        var result = if (number.isDigitsOnly()) {
            ayatText.substring(ayatText.indexOf('.') + 2, ayatText.length)
        } else ayatText
        while (result.indexOf("<sup>") >= 0) {
            result = result.removeRange(result.indexOf("<sup>"), result.indexOf("</sup>") + 6)
        }
        return "$subject\n\n$result"
    }

    private fun getNumberFromAyatText(ayatText: String): Int {
        if (ayatText.indexOf('.') < 0) return 0
        val number = ayatText.substring(0, ayatText.indexOf('.'))
        return if (number.isDigitsOnly()) {
            number.toInt()
        } else {
            0
        }
    }
}