package json.models

import json.models.JsonElement

/**
 * A Json containing a Null
 *
 * @constructor Creates a Json containing the value
 */
class JsonNull(): JsonElement() {

    val value: Unit? = null

    /**
     * Element wise comparison between two instances of JsonElement
     *
     * @param other Instance of JsonElement to compare
     * @return Boolean value indicating if both values are JsonNull
     */
    override fun equals(other: Any?): Boolean = other is JsonNull

    /**
     * Hash code method for a JsonNull instance
     *
     * @return Int value of hash code
     */
    override fun hashCode(): Int = 0

    /**
     * Serializes the Element into a Json compatible string
     *
     * @return the serialized Element
     */
    override fun serialize(): String = "null"
}