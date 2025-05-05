package json.primitives

import json.JsonElement

data class JsonNumber(
    val value: Number
): JsonElement() {

    override fun serialize(): String = value.toString()
}