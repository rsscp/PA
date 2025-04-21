package json.primitives

import json.JsonElement

data class JsonNull(
    val value: Unit? = null
): JsonElement() {

    override fun accept(visitor: (JsonElement) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun serialize(): String = "null"
}