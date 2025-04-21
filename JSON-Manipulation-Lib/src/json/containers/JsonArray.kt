package json.containers

import json.JsonElement

class JsonArray(
    val elements: MutableList<JsonElement> = mutableListOf()
): JsonContainer<JsonElement>() {

    fun map(map: (JsonElement) -> JsonElement):JsonArray {
        val newArray = elements.map(map).toMutableList()
        return JsonArray(newArray)
    }

    override fun filter(filter: (JsonElement) -> Boolean):JsonArray{
        val newArray = elements.filter(filter).toMutableList()
        return JsonArray(newArray)
    }

    override fun serialize(): String = "[" + elements.joinToString { it.toString() } + "]"

    fun getProperty(key: Int): JsonElement? = elements[key]
}