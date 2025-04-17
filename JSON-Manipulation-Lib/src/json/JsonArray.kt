package json

import json.interfaces.Filterable

class JsonArray(
    val properties: MutableList<Json>
): Json() {

    fun map(map: () -> Unit){

    }

    fun filter(filter: ( Json) -> Boolean):JsonArray{
        val newArray = properties.filter(filter).toMutableList()
        return JsonArray(newArray)
    }
    override fun toString(): String = "[" + properties.joinToString { it.toString() } + "]"

    fun getProperty(key: Int):Json? = properties[key]
}