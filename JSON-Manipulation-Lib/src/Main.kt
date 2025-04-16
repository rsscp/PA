import json.JsonObject
import json.Json
import json.JsonArray
import json.interfaces.Filterable
import kotlin.reflect.KClass

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val j: Json = JsonObject()
    val list: MutableList<Json> = mutableListOf()
    list.add(j)
}