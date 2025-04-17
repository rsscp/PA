package json.interfaces

import json.Json
import json.JsonObject

interface Filterable {
    fun filter(filter: (String, Json) -> Boolean)
}