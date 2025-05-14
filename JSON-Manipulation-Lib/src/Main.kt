import json.JsonElement
import json.containers.JsonArray
import json.containers.JsonObject
import json.converter.convert
import json.primitives.JsonBoolean
import json.primitives.JsonNull
import json.primitives.JsonNumber
import json.primitives.JsonString
import org.junit.jupiter.api.Assertions.assertEquals


enum class Color {
    RED, GREEN, BLUE
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    convert(Color.RED)
}