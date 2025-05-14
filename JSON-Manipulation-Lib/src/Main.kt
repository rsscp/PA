import json.converter.convert
import json.rest.Server


enum class Color {
    RED, GREEN, BLUE
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    Server::class.members.forEach { println(it) }
}