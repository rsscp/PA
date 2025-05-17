package json.rest

import json.converter.convert
import json.models.JsonElement
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.hasAnnotation

class MappingHandler(
    controller: Any,
    private val function: KFunction<*>,
    private val method: HttpMethod,
    private val path: String
) {
    private val pathParameterized: MutableList<Pair<String, KParameter?>> = mutableListOf()
    private val headerParameterized: MutableList<KParameter> = mutableListOf()

    private val parametersReady: MutableMap<KParameter, Any>

    init {
        fillPathParameterized()
        fillHeaderParameterized()
        parametersReady = mutableMapOf(
            function.parameters[0] to controller
        )
    }
    
    private fun fillPathParameterized() {
        val split = path
            .substring(1)
            .split("/")

        split.forEach { part ->
            val regex = Regex("\\{\\w+}")
            val name = regex.find(part)
                ?.value
                ?.replace("{", "")
                ?.replace("}", "")
                ?: part
            val parameter = function.parameters.find {
                it.name == name
            }
            pathParameterized.add(name to parameter)
        }
    }
    
    private fun fillHeaderParameterized() {
        function.parameters
            .filter { it.hasAnnotation<HeaderParam>() }
            .forEach { parameter ->
                val name = parameter.name
                    ?: throw IllegalArgumentException("Header parameter must have a name")
                headerParameterized.add(parameter)
            }
    }

    fun executeReady(path: String, httpMethod: String): Boolean {
        val match = mutableListOf<Boolean>()
        val receivedMethod = HttpMethod.valueOf(httpMethod.uppercase())
        val split = path
            .substring(1)
            .split("/")

        if (receivedMethod != method)
            return false

        pathParameterized.forEachIndexed { i, pair ->
            val name = pair.first
            val param = pair.second

            if (param != null) {
                match.add(true)
                putParameterReady(param, split[i])
            } else if (name == split[i]) {
                match.add(true)
            } else {
                return false
            }
        }

        return match.all { it }
    }

    private fun putParameterReady(parameter: KParameter, value: String) {
        when (parameter.type.classifier) {
            String::class -> parametersReady[parameter] = value
            Int::class -> parametersReady[parameter] = value.toInt()
            Double::class -> parametersReady[parameter] = value.toDouble()
            Boolean::class -> parametersReady[parameter] = value.toBoolean()
        }
    }

    fun executeClear() {
        parametersReady.clear()
    }

    fun execute(): JsonElement {
        return convert(function.callBy(parametersReady))
    }
}