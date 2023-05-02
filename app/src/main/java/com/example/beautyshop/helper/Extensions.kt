package com.example.beautyshop.helper

import android.content.Context
import com.example.beautyshop.conventions.SharedKeys

inline fun <reified T> Context.shared(sharedKey: SharedKeys): T {
    return when (sharedKey.type) {
        Boolean::class.java -> {
            UserData.getBoolByKey(this, SharedKeys.SharedName.key, sharedKey.key) as T
        }
        Int::class.java -> {
            UserData.getIntByKey(this, SharedKeys.SharedName.key, sharedKey.key) as T
        }
        Float::class.java -> {
            UserData.getFloatByKey(this, SharedKeys.SharedName.key, sharedKey.key) as T
        }
        Long::class.java -> {
            UserData.getLongByKey(this, SharedKeys.SharedName.key, sharedKey.key) as T
        }
        String::class.java -> {
            val value = UserData.getStringByKey(this, SharedKeys.SharedName.key, sharedKey.key)
            if (value == null) {
                "" as T
            } else {
                value as T
            }
        }
        else -> "" as T
    }
}

fun Context.saveShared(sharedKey: SharedKeys, value: Any?) {
    if (value == null) {
        removeShared(sharedKey)
        return
    }
    UserData.setValueByKey(this, SharedKeys.SharedName.key, sharedKey.key, value)
}

fun Context.removeShared(sharedKey: SharedKeys) {
    UserData.removeByKey(this, SharedKeys.SharedName.key, sharedKey.key)
}