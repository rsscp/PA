package json.models

import kotlin.reflect.KClass

/**
 * Json array representation containing elements of type JsonElement
 */
class JsonArray private constructor(
    private val elements: MutableList<JsonElement> = mutableListOf()
): JsonContainer() {

    /**
     * Companion object containing factory methods for the JsonArray class
     */
    companion object Constructor {

        /**
         * Factory method of JsonArray
         *
         * @param elementsArray Array of JsonElement instances
         */
        fun jsonArrayOf(vararg elementsArray: JsonElement): JsonArray {
            val elements: MutableList<JsonElement> = mutableListOf()

            for (element in elementsArray)
                elements.add(element)

            return JsonArray(elements)
        }

        /**
         * Factory method of JsonArray
         *
         * @param elementsList List of JsonElement instances
         */
        fun jsonArrayOf(elementsList: List<JsonElement>): JsonArray {
            return JsonArray(elementsList.toMutableList())
        }
    }

    /**
     * Verifies if this instance only contains JsonElements of the same type
     *
     * @return True if all have the same type and false otherwise
     */
    fun isSameType(): Boolean {
        if (elements.isNotEmpty()) {
            val type: KClass<out Any> = elements[0]::class
            return type != JsonNull::class && elements.all { it::class == type }
        }
        return true
    }

    /**
     * Applies the [mapping] function to all elements of this instance
     *
     * @return New JsonArray instance with mapped elements
     */
    fun map(mapping: (JsonElement) -> JsonElement): JsonArray {
        val newArray = elements.map(mapping).toMutableList()
        return JsonArray(newArray)
    }

    /**
     *
     *
     * @return Number of elements contained by this instance
     */
    fun size(): Int = elements.size

    /**
     * Copies this instance
     *
     * @return copy
     */
    fun copy(): JsonArray {
        return JsonArray(elements.toMutableList())
    }

    /**
     * Hash code method for this instance
     *
     * @return Int value unique for this instance
     */
    override fun hashCode(): Int {
        return elements.hashCode()
    }

    /**
     * Compare JsonArray instance to [other]
     * Does elements wise comparison if [other] is also instance of JsonArray
     *
     * @param other Instance of JsonArray to compare
     * @return Boolean value indicating if [other] is instance of JsonArray and if the compared instances have the same content
     */
    override fun equals(other: Any?): Boolean {
        return other is JsonArray && elements == other.elements
    }

    /**
     * Getter for array element using [index]
     *
     * @param index Index for the element
     * @return Element at [index]
     */
    operator fun get(index: Int): JsonElement? {
        return elements[index]
    }

    /**
     * Sets array element at [index] to [value]
     *
     * @param index Index for the element to modify
     * @param value New value for the element to modify
     */
    operator fun set(index: Int, value: JsonElement) {
        elements[index] = value
    }

    /**
     * Removes element within this instance using it's index
     *
     * @param index for the element to remove
     * @return Removed element or null if not found.
     */
    fun removeAt(index: Int): JsonElement {
        return elements.removeAt(index)
    }

    /**
     * Removes element within this instance using it's value
     *
     * @param value equaling the element to remove
     * @return boolean value indicating if the element was found and removed
     */
    fun remove(value: JsonElement): Boolean {
        return elements.remove(value)
    }

    /**
     * Applies a filter to the elements of this instance
     *
     * @return the filtered JsonArray
     */
    fun filter(check: (JsonElement) -> Boolean): JsonArray {
        val filtered = elements.filter { check(it) }
        return JsonArray(filtered.toMutableList())
    }

    /**
     * Iterates the list recursively applying [visitor] to this instance and each of it's elements
     */
    override fun accept(visitor: (JsonElement) -> Unit) {
        super.accept(visitor)
        elements.forEach { it.accept(visitor) }
    }

    /**
     * Serializes this instance into a valid json string
     *
     * @return the serialized list
     */
    override fun serialize(): String =
        "[" + elements.joinToString(",") { it.serialize() } + "]"
}