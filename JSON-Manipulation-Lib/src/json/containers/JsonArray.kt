package json.containers

import json.JsonElement

class JsonArray(
    vararg elementsArray: JsonElement,
): JsonElement(), Filterable<JsonArray, JsonElement> {

    val elements: MutableList<JsonElement> = mutableListOf()

    init {
        for (element in elementsArray) {
            elements.add(element)
        }
    }

    fun map(map: () -> Unit) {
        //TODO
    }

    override fun filter(filter: (JsonElement) -> Boolean): JsonArray{
        val newArray = elements.filter(filter)
        return JsonArray(newArray)
    }

    override fun accept(visitor: (JsonElement) -> Unit): Unit {
        visitor(this)
        elements.forEach { it.accept(visitor) }
    }

    override fun serialize(): String = "[" + elements.joinToString { it.toString() } + "]"

    operator fun get(key: Int): JsonElement = elements[key]//TODO ver se está dentro do tamanho da array
}