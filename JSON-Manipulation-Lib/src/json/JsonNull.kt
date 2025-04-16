package json

class JsonNull: Json() {
    fun visit(visitor: () -> Unit) {

    }

    override fun toString(): String = "null"
}