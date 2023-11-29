package com.buranchikov.fairprice

import android.content.Context
import android.content.SharedPreferences

class SplashPreferences(val context: Context) {
    val preferences = context.getSharedPreferences("fairPrice", Context.MODE_PRIVATE)

    fun setFlag() {
        preferences.edit().putInt("splash_viewed", 1)
    }
    fun getFlag(): Int {
        return preferences.getInt("splash_viewed", 0)

    }
}