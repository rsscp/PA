package json.containers

import json.JsonElement

class JsonObject(
    private val properties: MutableMap<String, JsonContainer> = mutableMapOf()
): JsonContainer() {

    override fun filter(filter: () -> Boolean) = listOf<JsonElement>()

    override fun serialize(): String = "{" + properties.entries.joinToString { (key, value) -> "${key}: ${value}"} + "}"

    fun getProperty(key: String): JsonContainer? = properties[key]
}