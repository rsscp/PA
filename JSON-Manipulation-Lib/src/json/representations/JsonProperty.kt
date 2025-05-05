package json.representations

import json.JsonElement

class JsonProperty(
    private val key: String,
    private val value: JsonElement
): JsonElement() {

    override fun accept(visitor: (JsonElement) -> Unit) {
        visitor(this)
    }

    override fun serialize(): String {
        TODO("Not yet implemented")
    }

    fun getKey(): String = key

    fun getPropertyValue(): JsonElement = value
}