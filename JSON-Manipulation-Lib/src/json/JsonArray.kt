package json

import json.interfaces.Filterable

class JsonArray(
    val properties: MutableList<Json>
): Json(), Filterable {

    fun map(map: () -> Unit){

    }
    override fun filter(filter: () -> Boolean){

    }
    override fun toString(): String = "[" + properties.joinToString { it.toString() } + "]"
}