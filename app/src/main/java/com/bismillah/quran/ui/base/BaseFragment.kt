package com.bismillah.quran.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment(resId: Int) : Fragment(resId) {
    fun toastSH(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLN(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}