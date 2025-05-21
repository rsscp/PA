package json.models

/**
 * Super class for all Json model classes
 *
 * @see JsonNull
 * @see JsonBoolean
 * @see JsonNumber
 * @see JsonString
 * @see JsonObject
 * @see JsonArray
 */
sealed class JsonElement {
    /**
     * Applies [visitor] to this instance of JsonElement
     */
    open fun accept(visitor: (JsonElement) -> Unit): Unit = visitor(this)

    /**
     * Abstract method to serialize the JsonElement into a valid json string
     */
    abstract fun serialize(): String
}