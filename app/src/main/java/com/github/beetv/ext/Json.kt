package com.github.beetv.ext

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlin.reflect.KClass

val objectMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())

fun Any.toJson(): String = objectMapper.writeValueAsString(this)

inline fun <reified T : Any> String.fromJson(kClass: KClass<T>): T =
    objectMapper.readValue(this, T::class.java)

fun <T> String.fromJson(typeReference: TypeReference<T>): T =
    objectMapper.readValue(this, typeReference)