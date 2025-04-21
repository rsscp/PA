package json.containers

import json.JsonElement
import json.representations.JsonProperty

class JsonObject(
    vararg propertiesArray: Pair<String, JsonElement>
): JsonElement(), Filterable<JsonObject, JsonProperty> {

    private val properties: MutableMap<String, JsonElement> = mutableMapOf()

    init {
        for (property in propertiesArray) {
            properties.put(property.first, property.second)
        }
    }

    override fun filter(filter: (property: JsonProperty) -> Boolean): JsonObject {
        val newObject = properties.filter{ (k, v) -> filter(JsonProperty(k,v)) }.toMutableMap()
        return JsonObject(newObject)
    }

    override fun accept(visitor: (JsonElement) -> Unit): Unit {
        visitor(this)
        properties.forEach { (k,v) -> JsonProperty(k,v).accept(visitor) }
    }

    override fun serialize(): String = "{" + properties.entries.joinToString { (key, value) -> "${key}: ${value}"} + "}"

    fun getProperty(key: String): JsonElement? = properties[key]
}