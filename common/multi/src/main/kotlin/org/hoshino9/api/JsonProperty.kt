package org.hoshino9.api

import com.google.gson.*
import java.io.File
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubclassOf

@Suppress("MemberVisibilityCanBePrivate", "unused")
class JsonProperty(val file: File, val default: JsonObject) {
    val json: JsonObject get() = if (file.exists()) file.readText().let { JsonParser().parse(it).asJsonObject } else default

    @Suppress("IMPLICIT_CAST_TO_ANY")
    inline operator fun <reified T> getValue(thisRef: Any?, property: KProperty<*>): T {
        val obj: JsonElement? = json[property.name] ?: default[property.name]

        return (if (obj == null) null else when (T::class) {
            JsonObject::class -> obj.asJsonObject
            JsonArray::class -> obj.asJsonArray
            JsonPrimitive::class -> obj.asJsonPrimitive
            JsonNull::class -> obj.asJsonNull

            String::class -> obj.asString
            Boolean::class -> obj.asBoolean
            Char::class -> obj.asCharacter

            Byte::class -> obj.asByte
            Short::class -> obj.asShort
            Int::class -> obj.asInt
            Long::class -> obj.asLong
            Float::class -> obj.asFloat
            Double::class -> obj.asDouble

            else -> throw IllegalArgumentException("Not in bounds: ${T::class}")
        }) as T
    }

    inline operator fun <reified T> setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        json.apply {
            if (value == null) {
                add(property.name, JsonNull.INSTANCE)
            } else {
                when (T::class) {
                    JsonObject::class, JsonArray::class, JsonPrimitive::class, JsonNull::class -> add(
                        property.name,
                        value as JsonElement
                    )
                    String::class -> addProperty(property.name, value as String)
                    Boolean::class -> addProperty(property.name, value as Boolean)
                    Char::class -> addProperty(property.name, value as Char)

                    else -> if (T::class.isSubclassOf(Number::class)) {
                        addProperty(property.name, value as Number)
                    } else throw IllegalArgumentException("Not in bounds: ${T::class}")
                }
            }
        }.toString().let {
            if (file.exists().not()) {
                file.parentFile.mkdirs()
                file.createNewFile()
            }

            file.writeText(it)
        }
    }
}