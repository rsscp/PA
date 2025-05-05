package json.containers

import json.JsonElement

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

    fun map(map: (JsonElement) -> JsonElement):JsonArray {
        val newArray = elements.map(map).toMutableList()
        return JsonArray(newArray)
    }

    fun size(): Int = elements.size

    inline operator fun <reified JsonType> get(index: Int): JsonType? {     //TODO Exception quando element não é JsonElement
        require(index < size() || index >= 0)

        val element = elements[index]

        if (element is JsonType)
            return element as JsonType
        else
            return null
    }

    operator fun set(index: Int, value: JsonElement) {
        elements[index] = value
    }

    override fun filter(check: (JsonElement) -> Boolean): JsonArray {
        val filtered = elements.filter { check(it) }
        return JsonArray(filtered)
    }

    override fun accept(visitor: (JsonElement) -> Unit): Unit {
        super.accept(visitor)
        elements.forEach { it.accept(visitor) }
    }

    override fun serialize(): String = "[" + elements.joinToString { it.toString() } + "]"
}