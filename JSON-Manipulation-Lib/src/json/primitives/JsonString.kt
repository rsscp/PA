package json.primitives

import json.JsonElement

data class JsonString(
    val value: String
): JsonElement() {

    override fun serialize(): String = "\"${value}\""
}