import json.containers.JsonObject
import json.primitives.JsonNull

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val o = JsonNull()
    println(o.getPrimitiveValue())
}