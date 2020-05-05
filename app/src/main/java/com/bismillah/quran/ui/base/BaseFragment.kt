package com.bismillah.quran.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bismillah.quran.Settings
import org.koin.android.ext.android.inject

open class BaseFragment(resId: Int) : Fragment(resId) {

    protected val settings: Settings by inject()

    fun toastSH(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLN(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}