package json.containers

import json.JsonElement

class JsonObject(
    private val properties: MutableMap<String, JsonElement> = mutableMapOf()
): JsonContainer<Pair<String, JsonElement>>() {

    override fun filter(filter: (Pair<String, JsonElement>) -> Boolean):JsonObject{
        val newObject = properties.filter{ (k, v) -> filter(Pair(k,v)) }.toMutableMap()
        return JsonObject(newObject)
    }

    override fun serialize(): String = "{" + properties.entries.joinToString { (key, value) -> "${key}: ${value}"} + "}"

    fun getProperty(key: String): JsonElement? = properties[key]
}