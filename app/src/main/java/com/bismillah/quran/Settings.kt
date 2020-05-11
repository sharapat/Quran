package com.bismillah.quran

import android.content.SharedPreferences

class Settings(private val preferences: SharedPreferences) {
    companion object {
        const val DARK_MODE = "darkMode"
        const val TEXT_SIZE = "TextSize"
        const val ARAB_TEXT_SIZE = "ArabTextSize"
        const val IS_APP_FIRST_LAUNCH = "isAppFirstLaunch"
        const val STEP = 2
    }

    fun setFirstLaunch() {
        preferences.edit().putBoolean(IS_APP_FIRST_LAUNCH, true).apply()
    }

    fun isAppFirstLaunch() : Boolean = preferences.getBoolean(IS_APP_FIRST_LAUNCH, false)

    fun changeAppMode() {
        if (isAppDarkMode()) {
            preferences.edit().putBoolean(DARK_MODE, false).apply()
        } else {
            preferences.edit().putBoolean(DARK_MODE, true).apply()
        }
    }

    fun isAppDarkMode() : Boolean = preferences.getBoolean(DARK_MODE, false)

    private fun setArabTextSize(size: Int) {
        if (size in 8..64) {
            preferences.edit().putInt(ARAB_TEXT_SIZE, size).apply()
        }
    }

    fun getArabTextSize() : Int = preferences.getInt(ARAB_TEXT_SIZE, 16)

    fun increaseArabTextSize() {
        setArabTextSize(getArabTextSize()+STEP)
    }

    fun decreaseArabTextSize() {
        setArabTextSize(getArabTextSize()-STEP)
    }

    fun increaseTextSize() {
        val currentTextSize = getTextSize()
        setTextSize(currentTextSize + STEP)
    }

    fun decreaseTextSize() {
        val currentTextSize = getTextSize()
        setTextSize(currentTextSize-STEP)
    }

    private fun setTextSize(size: Int) {
        if (size in 8..64) {
            preferences.edit().putInt(TEXT_SIZE, size).apply()
        }
    }

    fun getTextSize() : Int = preferences.getInt(TEXT_SIZE, 16)
}