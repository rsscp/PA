package json.containers

import json.JsonElement
import json.primitives.JsonPrimitive

abstract class JsonContainer(): JsonElement() {
    abstract fun filter(filter: () -> Boolean): List<JsonElement>
}