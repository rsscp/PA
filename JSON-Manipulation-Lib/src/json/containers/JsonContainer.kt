package json.containers

import json.JsonElement

abstract class JsonContainer<T>: JsonElement() {

    abstract fun filter(check: (T) -> Boolean): JsonContainer<T>
}