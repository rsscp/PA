package json.primitives

class JsonString(value: String): JsonPrimitive<String>(value) {

    override fun serialize(): String = "\"${value}\""
}