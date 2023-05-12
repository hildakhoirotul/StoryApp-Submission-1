package com.example.storyapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.storyapp.utils.Constanta.EMAIL
import com.example.storyapp.utils.Constanta.IS_LOGIN
import com.example.storyapp.utils.Constanta.NAME
import com.example.storyapp.utils.Constanta.PREFS_NAME
import com.example.storyapp.utils.Constanta.TOKEN
import com.example.storyapp.utils.Constanta.USER_ID

internal class Preferences(context: Context) {

    private var pref: SharedPreferences = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun setStringPreference(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun setBooleanPreference(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun clearPreference(){
        editor.clear().apply()
    }

    val token = pref.getString(TOKEN, "")
    val UserId = pref.getString(USER_ID, "")
    val isLogin = pref.getBoolean(IS_LOGIN, false)
    val name = pref.getString(NAME, "")
    val email = pref.getString(EMAIL, "")
}