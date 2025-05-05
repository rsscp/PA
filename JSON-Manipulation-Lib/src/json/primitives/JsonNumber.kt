package json.primitives

import json.JsonElement

data class JsonNumber(
    val value: Number
): JsonElement() {

    fun toInt(): Int = value.toInt()
    fun toDouble(): Double = value.toDouble()
    fun toLong(): Long = value.toLong()
    fun toFloat(): Float = value.toFloat()

    override fun serialize(): String = value.toString()
}