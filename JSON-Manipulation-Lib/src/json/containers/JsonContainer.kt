package json.containers

import json.JsonElement
import json.primitives.JsonPrimitive
import json.primitives.JsonString

abstract class JsonContainer<T>(): JsonElement() {
    abstract fun filter(filter: (T) -> Boolean): JsonContainer<T>
}