package json.primitives

class JsonNumber(value: Number): JsonPrimitive<Number>(value) {

    fun toInt(): Int = value.toInt()
    fun toDouble(): Double = value.toDouble()
    fun toLong(): Long = value.toLong()
    fun toFloat(): Float = value.toFloat()

    override fun serialize(): String = value.toString()
}