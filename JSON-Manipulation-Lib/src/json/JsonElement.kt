package json

abstract class JsonElement {
    abstract fun accept(visitor: (JsonElement) -> Unit): Unit
    abstract fun serialize(): String
}