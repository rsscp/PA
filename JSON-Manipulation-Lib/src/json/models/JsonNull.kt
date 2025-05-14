package json.models

import json.JsonElement

class JsonNull(): JsonElement() {

    val value: Unit? = null

    override fun equals(other: Any?): Boolean = other is JsonNull

    override fun hashCode(): Int = 0

    /**
     * Serializes the Element into a Json compatible string
     *
     * @return the serialized Element
     */
    override fun serialize(): String = "null"
}