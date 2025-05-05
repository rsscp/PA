package json.containers

import json.JsonElement
import json.representations.JsonProperty

class JsonObject(
    vararg propertiesArray: Pair<String, JsonElement>
): JsonContainer<JsonProperty>() {

    val properties: MutableMap<String, JsonElement> = mutableMapOf()

    init {
        for (property in propertiesArray) {
            properties.put(property.first, property.second)
        }
    }

    private constructor(propertiesList: List<JsonProperty>): this() {
        propertiesList.forEach { properties.put(it.getKey(), it.getPropertyValue()) }
    }

    inline operator fun <reified JsonType> get(key: String): JsonType? {
        require(key != "")

        val element = properties[key] ?: return null

        if (element is JsonType)
            return element as JsonType
        else
            return null
    }

    operator fun set(key: String, value: JsonElement) {
        properties[key] = value
    }

    override fun filter(check: (property: JsonProperty) -> Boolean): JsonObject {
        val filtered = JsonObject()
        properties.forEach {
            if (check(JsonProperty(it.key, it.value)))
                filtered.properties.put(it.key, it.value)
        }
        return filtered
    }

    override fun accept(visitor: (JsonElement) -> Unit): Unit {
        super.accept(visitor)
        properties.forEach { JsonProperty(it.key, it.value).accept(visitor) }
    }

    override fun serialize(): String = "{" + properties.entries.joinToString { (key, value) -> "${key}: ${value}"} + "}"
}