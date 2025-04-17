package json.primitives

class JsonNull: JsonPrimitive<Unit?>(null) {

    override fun getValue(): Unit? = null

    override fun serialize(): String = "null"
}