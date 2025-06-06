package json.converter

import kotlin.reflect.full.memberProperties

import json.models.JsonElement
import json.models.JsonArray.Constructor.jsonArrayOf
import json.models.JsonObject.Constructor.jsonObjectOf
import json.models.JsonBoolean
import json.models.JsonNull
import json.models.JsonNumber
import json.models.JsonString
import kotlin.collections.map


/**
 * Convert data class instance into a JsonElement instance
 *
 * @param obj Reference object for JsonElement creation
 * @return New JsonElement instance corresponding to
 */
fun convert(obj: Any?): JsonElement {

    if (obj == null)
        return JsonNull()

    if (obj::class.isData) {
        return convertTypeData(obj)
    }

    return when (obj) {
        is Number -> JsonNumber(obj)
        is Boolean -> JsonBoolean(obj)
        is String -> JsonString(obj)
        is Enum<*> -> JsonString(obj.toString())
        is List<*> -> convertTypeList(obj)
        is Map<*, *> -> convertTypeMap(obj)
        else -> throw IllegalArgumentException(
            "Class type not supported" +
            "\tMust be data class, List, Map, or primitive"
        )
    }
}

internal fun convertTypeData(obj: Any): JsonElement = jsonObjectOf (
    obj::class.memberProperties.map {
        val value = it.getter.call(obj)
        it.name to convert(value)
    }
)

@Suppress("UNCHECKED_CAST")
internal fun convertTypeMap(obj: Map<*,*>): JsonElement = jsonObjectOf(
    (obj as? MutableMap<String, *>)
        ?.mapValues { convert(it.value) }
        ?: throw IllegalArgumentException("Map must have keys of type String")
)

internal fun convertTypeList(obj: List<*>): JsonElement = jsonArrayOf(
        obj.map { convert(it) }
)