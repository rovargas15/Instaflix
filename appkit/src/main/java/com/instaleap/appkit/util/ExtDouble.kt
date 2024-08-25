package com.instaleap.appkit.util

import kotlin.math.min

fun <T> limitDecimals(
    input: T,
    maxDecimals: Int,
): String {
    val result = input.toString()
    val lastIndex = result.length - 1
    var pos = lastIndex
    while (pos >= 0 && result[pos] != '.') {
        pos--
    }
    return if (maxDecimals < 1 && pos >= 0) {
        result.substring(0, min(pos, result.length))
    } else if (pos >= 0) {
        result.substring(0, min(pos + 1 + maxDecimals, result.length))
    } else {
        return result
    }
}

fun Double?.toVote(): String {
    if (this == null) {
        return "0"
    }
    return "${limitDecimals(this, 1)}/10"
}
