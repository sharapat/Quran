package com.bismillah.quran

import android.content.SharedPreferences

class Settings(private val preferences: SharedPreferences) {
    companion object {
        const val TEXT_SIZE = "TextSize"
    }

    fun setTextSize(size: Int) {
        if (size in 8..64) {
            preferences.edit().putInt(TEXT_SIZE, size).apply()
        }
    }

    fun getTextSize() : Int = preferences.getInt(TEXT_SIZE, 16)
}