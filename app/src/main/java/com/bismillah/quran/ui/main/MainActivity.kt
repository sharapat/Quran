package com.bismillah.quran.ui.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private val settings: Settings by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        updateTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigation, navHostFragment.navController)
    }

    private fun updateTheme() {
        if (settings.isAppDarkMode()) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.LightTheme)
        }
    }

    fun updateThemeAndRecreateActivity() {
        updateTheme()
        recreate()
    }
}
