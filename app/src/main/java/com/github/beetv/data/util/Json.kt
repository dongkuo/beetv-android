package com.github.beetv.data.util

import com.google.gson.Gson
import kotlin.reflect.KClass

val gson = Gson()

fun Any.toJson(): String = gson.toJson(this)

inline fun <reified T : Any> String.fromJson(kClass: KClass<T>): T =
    gson.fromJson(this, T::class.java)