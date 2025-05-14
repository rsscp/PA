package json.models

import json.JsonElement

/**
 * A Json containing a String
 *
 * @property value the value of the String
 * @constructor Creates a Json containing the value
 */
data class JsonString(
    val value: String
): JsonElement() {

    /**
     * Serializes the Element into a Json compatible string
     *
     * @return the serialized element
     */
    override fun serialize(): String = "\"${value}\""
}