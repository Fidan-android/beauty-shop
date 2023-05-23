package com.example.beautyshop.helper

import android.content.Context
import com.example.beautyshop.conventions.SharedKeys
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

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

fun Calendar.compareFirstDayOfWeek(): Int {
    return when (this.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> 0
        Calendar.TUESDAY -> 1
        Calendar.WEDNESDAY -> 2
        Calendar.THURSDAY -> 3
        Calendar.FRIDAY -> 4
        Calendar.SATURDAY -> 5
        Calendar.SUNDAY -> 6
        else -> 0
    }
}

fun File.copyInputStreamToFile(inputStream: InputStream) {
    this.outputStream().use { fileOut ->
        inputStream.copyTo(fileOut)
    }
}

fun Date.toIso(locale: Locale = Locale.getDefault(), timeZone: TimeZone? = null): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", locale)
    if (timeZone != null) {
        simpleDateFormat.timeZone = timeZone
    }
    return simpleDateFormat
        .format(this)
        .replace("GMT", "")
}
fun String.isEmailValid(): Boolean {
    val emailRegex = "^[_A-Za-z\\d-+]+(\\.[_A-Za-z\\d-]+)*@" + "[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$"
    val pattern = Pattern.compile(emailRegex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}