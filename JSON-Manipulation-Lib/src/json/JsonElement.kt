package json

/**
 * The super class for all the primitives
 *
 */
abstract class JsonElement {

    open fun accept(visitor: (JsonElement) -> Unit): Unit = visitor(this)

    abstract fun serialize(): String
}