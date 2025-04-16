package json.primitives

class JsonBoolean(value: Boolean): JsonPrimitive<Boolean>(value) {

    override fun serialize(): String = value.toString()
}