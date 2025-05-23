package com.vanshika.klf.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object TokenManager {
    private const val PREF_NAME = "app_prefs"
    private const val TAG = "TokenManager"
    private const val TOKEN_KEY = "auth_token"
    private const val TOKEN_TIME_KEY = "token_time"
    private const val USER_ID_KEY = "user_id"
    private const val IS_LOGGED_IN_KEY = "is_logged_in"
    private const val TOKEN_EXPIRATION_TIME = 6 * 60 * 60 * 1000L // 6 hours in milliseconds

    fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(TOKEN_KEY, token)
            putLong(TOKEN_TIME_KEY, System.currentTimeMillis())
            apply()
        }
        Log.d(TAG, "Token saved successfully: $token")
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val token = sharedPreferences.getString(TOKEN_KEY, null)
        Log.d(TAG, "Retrieved token: ${token ?: "No token found"}")
        return token
    }

    fun isTokenExpired(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedTime = sharedPreferences.getLong(TOKEN_TIME_KEY, 0L)
        val currentTime = System.currentTimeMillis()
        val expired = (currentTime - savedTime) > TOKEN_EXPIRATION_TIME
        Log.d(TAG, "Token expiration check: Expired = $expired")
        return expired
    }

    fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(TOKEN_KEY)
            remove(TOKEN_TIME_KEY)
            apply()
        }
        Log.d(TAG, "Token cleared successfully")
    }

    fun getUserId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(USER_ID_KEY, 0)
    }

    fun setUserId(context: Context, userId: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(USER_ID_KEY, userId).apply()
    }

    fun isUserLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)
    }

    fun saveLoginState(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN_KEY, true).apply()
    }

    fun clearLoginState(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN_KEY, false).apply()
    }
}
