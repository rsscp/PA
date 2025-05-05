package json.containers

import json.JsonElement
import json.representations.JsonProperty

/**
 * A Json containing a List of JsonProperty
 *
 * @property propertiesArray the list of Pair<String, JsonElement>
 * @constructor Creates a List containing the provided elements
 */
class JsonObject(
    vararg propertiesArray: Pair<String, JsonElement>
): JsonContainer<JsonProperty>() {

    val properties: MutableMap<String, JsonElement> = mutableMapOf()        // TODO tornar privado

    init {
        for (property in propertiesArray) {
            properties.put(property.first, property.second)
        }
    }

    /**
     * Gets object property using [key]
     *
     * @param key Key identifying the property to get from [properties]
     * @return the filtered JsonObject
     */
    inline operator fun <reified JsonType> get(key: String): JsonType? {
        require(key != "")

        val element = properties[key] ?: return null

        if (element is JsonType)
            return element as JsonType
        else
            return null
    }

    /**
     * Applies a filter to the Map
     *
     * @return the filtered JsonObject
     */
    override fun filter(check: (property: JsonProperty) -> Boolean): JsonObject {
        val filtered = JsonObject()
        properties.forEach {
            if (check(JsonProperty(it.key, it.value)))
                filtered.properties.put(it.key, it.value)
        }
        return filtered
    }

    /**
     * Iterates the map recursively applying [visitor]
     *
     */
    override fun accept(visitor: (JsonElement) -> Unit): Unit {
        super.accept(visitor)
        properties.forEach { JsonProperty(it.key, it.value).accept(visitor) }
    }

    /**
     * Serializes the Map into a Json compatible string
     *
     * @return the serialized Map
     */
    override fun serialize(): String = "{" + properties.entries.joinToString { (key, value) -> "${key}: ${value}"} + "}"

    /**
     * Getter for [properties]
     *
     * @return the property at the given [key]
     */
    fun getProperty(key: String): JsonElement? = properties[key]
}