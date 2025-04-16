package json

import json.interfaces.Filterable

class JsonObject(
    private val properties: MutableMap<String, Json> = mutableMapOf()
): Json(), Filterable {
    override fun filter(filter: () -> Boolean){

    }
    override fun serialize(): String = "{" + properties.entries.joinToString { (key, value) -> "${key}: ${value.serialize()}"} + "}"

    fun getProperty(key: String):Json? = properties[key]
}