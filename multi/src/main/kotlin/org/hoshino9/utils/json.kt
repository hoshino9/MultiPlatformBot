@file:JvmName("JsonUtils")

package org.hoshino9.utils

import com.google.gson.JsonElement
import com.google.gson.JsonObject

@Suppress("unused")
class JsonBuilder
@JvmOverloads
constructor(private val original: JsonObject = JsonObject()) {
    @JvmName("add")
    infix fun String.to(value: JsonElement) {
        original.add(this, value)
    }

    @JvmName("add")
    infix fun String.to(value: Number): JsonBuilder {
        original.addProperty(this, value)
        return this@JsonBuilder
    }

    @JvmName("add")
    infix fun String.to(value: Char): JsonBuilder {
        original.addProperty(this, value)
        return this@JsonBuilder
    }

    @JvmName("add")
    infix fun String.to(value: Boolean): JsonBuilder {
        original.addProperty(this, value)
        return this@JsonBuilder
    }

    @JvmName("add")
    infix fun String.to(value: String): JsonBuilder {
        original.addProperty(this, value)
        return this@JsonBuilder
    }

    fun build(): JsonObject {
        return original.deepCopy()
    }
}

inline fun json(init: JsonBuilder.() -> Unit): JsonObject {
    return JsonBuilder().apply(init).build()
}