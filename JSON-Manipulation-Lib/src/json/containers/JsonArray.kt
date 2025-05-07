package json.containers

import json.JsonElement

/**
 * A Json containing a List of JsonElement
 *
 * @property elementsArray the list of JsonElement
 * @constructor Creates a List containing the provided elements
 */
class JsonArray(
    vararg elementsArray: JsonElement,
): JsonContainer<JsonElement>() {

    val elements: MutableList<JsonElement> = mutableListOf()

    init {
        for (element in elementsArray) {
            elements.add(element)
        }
    }

    private constructor(elementsList: List<JsonElement>): this() {
        elements.addAll(elementsList)
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


    /**
     * Gets array element using [index]
     *
     * @param index Index for the element to get from [elements]
     * @return the filtered JsonArray
     */
    inline operator fun <reified JsonType> get(index: Int): JsonType? {     //TODO Exception quando element não é JsonElement
        require(index < size() || index >= 0)

        val element = elements[index]

        if (element is JsonType)
            return element as JsonType
        else
            return null
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
     * Applies a filter to the List
     *
     * @return the filtered JsonArray
     */
    override fun filter(check: (JsonElement) -> Boolean): JsonArray {
        val filtered = elements.filter { check(it) }
        return JsonArray(filtered)
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