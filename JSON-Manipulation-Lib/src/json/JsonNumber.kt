package json

class JsonNumber(val value: Number): Json() {
    fun visit(visitor: () -> Unit) {

    }

    override fun toString(): String = value.toString()
}