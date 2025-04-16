package json.containers

import json.JsonElement

class JsonArray(
    val elements: MutableList<JsonContainer> = mutableListOf()
): JsonContainer() {

    fun map(map: () -> Unit) {
        //TODO
    }

    override fun filter(filter: () -> Boolean) = listOf<JsonElement>()

    override fun serialize(): String = "[" + elements.joinToString { it.toString() } + "]"

    fun getProperty(key: Int): JsonElement? = elements[key]
}