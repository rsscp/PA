package json.primitives

import json.JsonElement

class JsonNull(): JsonElement() {

    val value: Unit? = null

    /**
     * Serializes the Element into a Json compatible string
     *
     * @return the serialized Element
     */
    override fun serialize(): String = "null"
}