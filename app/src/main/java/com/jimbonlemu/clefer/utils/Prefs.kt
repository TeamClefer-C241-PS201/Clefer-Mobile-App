package com.jimbonlemu.clefer.utils

import android.content.Context
import android.content.SharedPreferences
import com.jimbonlemu.clefer.source.remote.response.LoginResult

object Prefs {
    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private const val PREFERENCE_NAME = "clefer.pref"
    private const val KEY_NAME = "key_name"
    private const val KEY_USERNAME = "key_username"
    private const val KEY_EMAIL = "key_email"
    private const val KEY_TOKEN = "key_token"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    val getName: String?
        get() = prefs.getString(KEY_NAME, "")
    val getUsername:String?
        get() = prefs.getString(KEY_USERNAME,"")
    val getEmail:String?
        get() = prefs.getString(KEY_EMAIL,"")
    val getToken: String?
        get() = prefs.getString(KEY_TOKEN, "")

    fun setLoginPrefs(loginResult: LoginResult) {
        editor.putString(KEY_NAME, loginResult.name)
        editor.putString(KEY_USERNAME, loginResult.username)
        editor.putString(KEY_EMAIL, loginResult.email)
        editor.putString(KEY_TOKEN, loginResult.token)
        editor.apply()
    }

    fun clearAllPreferences() {
        editor.remove(KEY_NAME)
        editor.remove(KEY_USERNAME)
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_TOKEN)
        editor.apply()
    }
}