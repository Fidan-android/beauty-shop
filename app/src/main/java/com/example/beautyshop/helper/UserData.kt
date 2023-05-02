package com.example.beautyshop.helper

import android.content.Context

/**
 * Class to work with Shared Preferences
 */
object UserData {

    fun setValueByKey(context: Context, dbName: String, key: String, value: Any?) {
        val shared = context.getSharedPreferences(dbName, Context.MODE_PRIVATE)
        shared.edit().remove(key).apply()
        when (value) {
            is String -> shared.edit().putString(key, value).apply()
            is Int -> shared.edit().putInt(key, value).apply()
            is Boolean -> shared.edit().putBoolean(key, value).apply()
            is Long -> shared.edit().putLong(key, value).apply()
            is Float -> shared.edit().putFloat(key, value).apply()
            is MutableSet<*> -> shared.edit().putStringSet(key, value as MutableSet<String>).apply()
        }
    }

    fun getStringByKey(context: Context, dbName: String, key: String): String? {
        val shared = context.getSharedPreferences(dbName, Context.MODE_PRIVATE)
        return shared.getString(key, null)
    }

    fun getIntByKey(context: Context, dbName: String, key: String): Int {
        val shared = context.getSharedPreferences(dbName, Context.MODE_PRIVATE)
        return shared.getInt(key, 0)
    }

    fun getBoolByKey(context: Context, dbName: String, key: String): Boolean {
        val shared = context.getSharedPreferences(dbName, Context.MODE_PRIVATE)
        return shared.getBoolean(key, false)
    }

    fun getLongByKey(context: Context, dbName: String, key: String): Long {
        val shared = context.getSharedPreferences(dbName, Context.MODE_PRIVATE)
        return shared.getLong(key, 0)
    }

    fun getFloatByKey(context: Context, dbName: String, key: String): Float {
        val shared = context.getSharedPreferences(dbName, Context.MODE_PRIVATE)
        return shared.getFloat(key, 0f)
    }

    fun getStringSetByKey(context: Context, dbName: String, key: String): MutableSet<String>? {
        val shared = context.getSharedPreferences(dbName, Context.MODE_PRIVATE)
        return shared.getStringSet(key, null)
    }

    fun removeByKey(context: Context, dbName: String, key: String) {
        val shared = context.getSharedPreferences(dbName, Context.MODE_PRIVATE)
        shared.edit().remove(key).apply()
    }

}