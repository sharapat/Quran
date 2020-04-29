package com.bismillah.quran

import android.content.SharedPreferences

class Settings(private val preferences: SharedPreferences) {
    companion object {
        const val TEXT_SIZE = "TextSize"
        const val ARAB_TEXT_SIZE = "ArabTextSize"
        const val STEP = 2
    }

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