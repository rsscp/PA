package json.primitives

class JsonNull: JsonPrimitive<Unit?>(null) {

    override fun getPrimitiveValue(): Unit? = null

    override fun serialize(): String = "null"
}