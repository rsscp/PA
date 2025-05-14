package json.containers

import json.JsonElement
import kotlin.reflect.KClass

/**
 * A json array containing elements of type JsonElement
 */
class JsonArray private constructor(
    private val elements: MutableList<JsonElement> = mutableListOf()
): JsonContainer<JsonElement>() {

    /**
     * Companion object containing factory methods for the JsonArray class
     */
    companion object Constructor {

        /**
         * Factory method of JsonArray
         *
         * @param elementsArrayArray Array of JsonElement instances
         */
        fun jsonArrayOf(vararg elementsArray: JsonElement): JsonArray {
            val elements: MutableList<JsonElement> = mutableListOf()

            for (element in elementsArray)
                elements.add(element)

            return JsonArray(elements)
        }
    }

    /**
     * Verifies if the JsonArray only has JsonElements of the same type
     *
     * @return true if all have the same type false if not
     */
    fun isSameType(): Boolean {
        if (elements.isNotEmpty()) {
            val type: KClass<out Any> = elements[0]::class
            return elements.all { it::class != type }
        }
        return true
    }

    /**
     * Applies the [map] to all elements of the List
     *
     * @return the new JsonArray
     */
    fun map(map: (JsonElement) -> JsonElement):JsonArray {
        val newArray = elements.map(map).toMutableList()
        return JsonArray(newArray)
    }

    /**
     * @return the size of element
     */
    fun size(): Int = elements.size

    fun copy(): JsonArray {
        return JsonArray(elements.toMutableList())
    }

    /**
     * Hash code method for a JsonArray instance
     *
     * @return Int value of hash code
     */
    override fun hashCode(): Int {
        return elements.hashCode()
    }

    /**
     * Element wise comparison between two instances of JsonArray
     *
     * @param other Instance of JsonArray to compare
     * @return Boolean value indicating if compared instances have the same content
     */
    override fun equals(other: Any?): Boolean {
        return other is JsonArray && elements == other.elements
    }

    /**
     * Gets array element using [index]
     *
     * @param index Index for the element to get from [elements]
     * @return the filtered JsonArray
     */
    operator fun get(index: Int): JsonElement? {
        return elements[index]
    }

    /**
     * Sets array element at [index] to [value]
     *
     * @param index of the element to modify
     * @param value New value for the element to modify
     * @return the filtered JsonArray
     */
    operator fun set(index: Int, value: JsonElement) {
        elements[index] = value
    }

    /**
     * Removes element within the json array
     *
     * @param index of the element to remove
     * @return the removed element or null if not found.
     */
    fun removeAt(index: Int): JsonElement {
        return elements.removeAt(index)
    }

    /**
     * Removes element within the json array
     *
     * @param value equaling the element to remove
     * @return boolean value indicating if the element was found and removed
     */
    fun remove(value: JsonElement): Boolean {
        return elements.remove(value)
    }

    /**
     * Applies a filter to the List
     *
     * @return the filtered JsonArray
     */
    fun filter(check: (JsonElement) -> Boolean): JsonArray {       //TODO override de interface ou super class?
        val filtered = elements.filter { check(it) }
        return JsonArray(filtered.toMutableList())
    }

    /**
     * Iterates the list recursively applying [visitor]
     *
     */
    override fun accept(visitor: (JsonElement) -> Unit): Unit {
        super.accept(visitor)
        elements.forEach { it.accept(visitor) }
    }

    /**
     * Serializes the List into a Json compatible string
     *
     * @return the serialized list
     */
    override fun serialize(): String = "[" + elements.joinToString { it.serialize() } + "]"
}