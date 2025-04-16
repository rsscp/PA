package json

class JsonBoolean(val value: Boolean): Json() {
    fun visit(visitor: () -> Unit) {

    }

    override fun toString(): String = value.toString()
}