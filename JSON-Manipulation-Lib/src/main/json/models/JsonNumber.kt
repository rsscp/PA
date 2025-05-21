package json.models

import json.models.JsonElement

/**
 * A Json containing a Number
 *
 * @property value the value of the Number
 * @constructor Creates a Json containing the value
 */
data class JsonNumber(
    val value: Number
): JsonElement() {

    /**
     * Converts the Number to an Int
     *
     * @return the [value] as an Int
     */
    fun toInt(): Int = value.toInt()

    /**
     * Converts the Number to a Double
     *
     * @return the [value] as a Double
     */
    fun toDouble(): Double = value.toDouble()

    /**
     * Converts the Number to a Long
     *
     * @return the [value] as a Long
     */
    fun toLong(): Long = value.toLong()

    /**
     * Converts the Number to a Float
     *
     * @return the [value] as a Float
     */
    fun toFloat(): Float = value.toFloat()

    /**
     * Serializes the Element into a Json compatible string
     *
     * @return the serialized element
     */
    override fun serialize(): String = value.toString()
}