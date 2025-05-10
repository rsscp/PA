package json.containers

import json.JsonElement

/**
 * The super class for JsonObject and JsoArray
 *
 * @param T the type of element in this container
 */
abstract class JsonContainer<T>: JsonElement() {

}