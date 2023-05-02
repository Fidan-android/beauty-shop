package com.example.beautyshop.conventions

import java.lang.reflect.Type

enum class SharedKeys(val key: String, val type: Type) {
    SharedName("beauty_shop", String::class.java),
    AccessToken("access_token", String::class.java)
}