package json.primitives

class JsonNumber(value: Number): JsonPrimitive<Number>(value) {

    override fun serialize(): String = value.toString()
}