package json

import json.interfaces.Filterable

class JsonObject(
    private val properties: MutableMap<String, Json> = mutableMapOf()
): Json() {
    fun filter(filter: (key:String, value:Json) -> Boolean):JsonObject{
        val newObject = properties.filter{ (k, v) -> filter(k, v) }.toMutableMap()
        return JsonObject(newObject)
    }
    override fun toString(): String = "{" + properties.entries.joinToString { (key, value) -> "${key}: ${value}"} + "}"

    fun getProperty(key: String):Json? = properties[key]
}