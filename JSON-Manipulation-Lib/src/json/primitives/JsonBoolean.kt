package json.primitives

import json.JsonElement

data class JsonBoolean(
    val value: Boolean
): JsonElement() {

    override fun serialize(): String = value.toString()
}