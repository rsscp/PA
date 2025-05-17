package tests

import json.rest.HeaderParam;
import json.rest.Method
import json.rest.PathParam;
import json.rest.Mapping;
import json.rest.HttpMethod

@Mapping("api")
class Controller {
    @Method(HttpMethod.GET)
    @Mapping("ints")
    fun demo(): List<Int> = listOf(1, 2, 3)

    @Method(HttpMethod.GET)
    @Mapping("pair")
    fun obj(): Pair<String, String> = Pair("um", "dois")

    @Method(HttpMethod.GET)
    @Mapping("path/{pathvar}")
    fun path(
        @PathParam pathvar: String
    ): String = pathvar + "!"

    @Method(HttpMethod.GET)
    @Mapping("args")
    fun args(
        @HeaderParam n: Int,
        @HeaderParam text: String
    ): Map<String, String> = mapOf(text to text.repeat(n))
}