package json.representations

import json.JsonElement

/**
 * A Json containing a pair of a key and a value
 *
 * @property key the key  for the map
 * @property value the value of the element
 * @constructor Creates a JsonProperty containing both properties
 */
class JsonProperty(
    private val key: String,
    private val value: JsonElement
): JsonElement() {

    /**
     * Applies [visitor] to the JsonProperty
     *
     */
    override fun accept(visitor: (JsonElement) -> Unit) {
        visitor(this)
    }

    override fun serialize(): String {
        TODO("Not yet implemented")
    }

    /**
     * Getter for [key]
     *
     * @return the [key]
     */
    fun getKey(): String = key

    /**
     * Getter for [value]
     *
     * @return the [value]
     */
    fun getPropertyValue(): JsonElement = value
}