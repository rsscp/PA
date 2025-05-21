package json.models

/**
 * A Json containing a Boolean
 *
 * @property value the value of the Boolean
 * @constructor Creates a Json containing the value
 */
data class JsonBoolean(
    val value: Boolean
): JsonElement() {

    /**
     * Serializes the Element into a Json compatible string
     *
     * @return the serialized Element
     */
    override fun serialize(): String = value.toString()
}