package json.primitives

import json.JsonElement

data class JsonBoolean(
    val value: Boolean
): JsonElement() {//TODO find a way to make this a data class without declaring a repeated value class member

    override fun accept(visitor: (JsonElement) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun serialize(): String = value.toString()
}