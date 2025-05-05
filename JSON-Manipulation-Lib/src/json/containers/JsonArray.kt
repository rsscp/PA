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

    val elements: MutableList<JsonElement> = mutableListOf()        //TODO tornar privado

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

    inline operator fun <reified JsonType> get(index: Int): JsonType? {
        require(index < size() || index >= 0)

        val element = elements[index]

        if (element is JsonType)
            return element as JsonType
        else
            return null
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
    override fun serialize(): String = "[" + elements.joinToString { it.toString() } + "]"
}