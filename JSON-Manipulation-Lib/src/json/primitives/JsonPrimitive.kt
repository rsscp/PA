package json.primitives

import json.JsonElement

abstract class JsonPrimitive<T>(
    protected val value: T
): JsonElement() {
    open fun getPrimitiveValue(): T = value
}