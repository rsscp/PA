package tests

import json.rest.Mapping;
import json.rest.HeaderParam;
import json.rest.PathParam;

@Mapping("api")
class Controller {
    @Mapping("ints")
    fun demo(): List<Int> = listOf(1, 2, 3)

    @Mapping("pair")
    fun obj(): Pair<String, String> = Pair("um", "dois")

    @Mapping("path/{pathvar}")
    fun path(
        @PathParam pathvar: String
    ): String = pathvar + "!"

    @Mapping("args")
    fun args(
        @HeaderParam n: Int,
        @HeaderParam text: String
    ): Map<String, String> = mapOf(text to text.repeat(n))
}