package json

class JsonString(val value: String): Json() {
    fun visit(visitor: () -> Unit) {

    }

    override fun serialize(): String = "\"${value}\""
}