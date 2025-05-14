package json.models

import json.JsonElement

/**
 * A json object containing a list of properties composed by a key of type String and a value of type JsonElement
 */
class JsonObject private constructor(
    private val properties: MutableMap<String, JsonElement> = mutableMapOf()
): JsonElement() {

    /**
     * Companion object containing factory methods for the JsonObject class
     */
    companion object Constructor {

        /**
         * Factory method of JsonObject
         *
         * @param propertiesArray Array of Pair<String, JsonElement> instances
         */
        fun jsonObjectOf(vararg propertiesArray: Pair<String, JsonElement>): JsonObject {
            val properties: MutableMap<String, JsonElement> = mutableMapOf()

            for (property in propertiesArray)
                properties.put(property.first, property.second)

            return JsonObject(properties)
        }

        /**
         * Factory method of JsonObject
         *
         * @param propertiesIt Iterable of Pair<String, JsonElement>
         */
        fun jsonObjectOf(propertiesIt: Iterable<Pair<String, JsonElement>>): JsonObject {
            val properties: MutableMap<String, JsonElement> = mutableMapOf()

            for (property in propertiesIt)
                properties.put(property.first, property.second)

            return JsonObject(properties)
        }

        /**
         * Factory method of JsonObject
         *
         * @param propertiesMap Map<String, JsonElement> to use for JsonObject creation
         */
        fun jsonObjectOf(propertiesMap: Map<String, JsonElement>): JsonObject {
            return JsonObject(propertiesMap.toMutableMap())
        }
    }

    /**
     * Iterates through the JsonObject to verify if every JsonArray contains only values of the same type for that array
     *
     * @return the combined returns of isSameType
     */
    fun checkArrayTypes(): Boolean {
        var result = true

        accept {
            if (it is JsonArray) {
                result = result && it.isSameType()
            }
        }

        return result
    }

    /**
     * Copy method for a JsonObject instance
     *
     * @return Identical JsonObject instance
     */
    fun copy(): JsonObject {
        return JsonObject(properties.toMutableMap())
    }

    /**
     * Hash code method for a JsonObject instance
     *
     * @return Int value of hash code
     */
    override fun hashCode(): Int {
        return properties.hashCode()
    }

    /**
     * Properties wise comparison between two instances of JsonObject
     *
     * @param other Instance of JsonObject to compare
     * @return Boolean value indicating if compared instances have the same content
     */
    override fun equals(other: Any?): Boolean {
        return other is JsonObject && properties == other.properties
    }

    /**
     * Gets object property using [key]
     *
     * @param key Key of type String identifying the property to get from an instance of JsonObject
     * @return the filtered JsonObject
     */
    operator fun get(key: String): JsonElement? {
        return properties[key]
    }

    /**
     * Sets object property identified by [key] to [value]
     *
     * @param key Key identifying the property to modify
     * @param value New value for the property to modify
     * @return the filtered JsonArray
     */
    operator fun set(key: String, value: JsonElement) {
        properties[key] = value
    }

    /**
     * Removes element within the json object
     *
     * @param key of the property to remove
     * @return removed element or null if [key] was not not found.
     */
    fun remove(key: String): JsonElement? {
        return properties.remove(key)
    }

    /**
     * Removes element within the json object
     *
     * @param key of the property to remove
     * @param value equaling the element to remove
     * @return boolean value indicating if the element was found and removed
     */
    fun remove(key: String, value: JsonElement): Boolean {
        return properties.remove(key, value)
    }

    /**
     * Applies a filter to the Map
     *
     * @param check Function with parameters key of type String and value of type JsonElement and returns a boolean value indicating of the evaluated property passes the filter
     * @return the filtered JsonObject
     */
    fun filter(check: (key: String, value: JsonElement) -> Boolean): JsonObject {               //TODO override????
        val filtered = properties.filter { check(it.key, it.value) }
        return JsonObject(filtered.toMutableMap())
    }

    /**
     * Iterates the map recursively applying [visitor]
     *
     * @param visitor Function with parameter of type JsonElement which acts on each property in a JsonObject instance
     */
    override fun accept(visitor: (JsonElement) -> Unit): Unit {
        super.accept(visitor)
        properties.forEach { it.value.accept(visitor) }
    }

    /**
     * Serializes the Map into a Json compatible string
     *
     * @return the serialized Map
     */
    override fun serialize(): String = "{" + properties.entries.joinToString { (key, value) -> "\"${key}\": " + value.serialize()} + "}"

}