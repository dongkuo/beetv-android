package com.github.beetv.util

class RandomUtils {

    companion object {
        fun randomString(size: Int): String {
            val nonceScope = "1234567890abcdefghijklmnopqrstuvwxyz"
            val scopeSize = nonceScope.length
            val nonceItem: (Int) -> Char = { nonceScope[(scopeSize * Math.random()).toInt()] }
            return Array(size, nonceItem).joinToString("")
        }
    }
}