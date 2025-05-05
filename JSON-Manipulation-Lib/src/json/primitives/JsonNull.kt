package json.primitives

import json.JsonElement

class JsonNull(): JsonElement() {

    val value: Unit? = null

    override fun serialize(): String = "null"
}