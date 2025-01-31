package com.rb.core.shared.ext

import android.util.Log
import kotlinx.serialization.json.Json

inline fun <reified T> Json.decode(string: String?): T? {
    if (string.isNullOrBlank()) {
        return null
    }
    return try {
        decodeFromString<T>(string)
    } catch (e: Exception) {
        Log.w("Json", e.stackTraceToString())
        null
    }
}

inline fun <reified T> Json.encode(t: T): String? {
    return try {
        encodeToString(t)
    } catch (e: Exception) {
        Log.w("Json", e.stackTraceToString())
        null
    }
}
