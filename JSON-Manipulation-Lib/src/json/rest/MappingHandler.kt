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
    private val queryParameterized: MutableList<KParameter> = mutableListOf()

    private val parametersReady: MutableMap<KParameter, Any>

    init {
        fillPathParameterized()
        fillQueryParameterized()
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
    
    private fun fillQueryParameterized() {
        function.parameters
            .filter { it.hasAnnotation<HeaderParam>() }
            .forEach { parameter ->
                val name = parameter.name
                    ?: throw IllegalArgumentException("Header parameter must have a name")
                queryParameterized.add(parameter)
            }
    }

    fun executeReady(httpMethod: String, path: String, query: String): Boolean {
        var ready = true
        val receivedMethod = HttpMethod.valueOf(httpMethod.uppercase())

        if (receivedMethod != method)
            return false

        ready = ready && putPathParametersReady(path)
        ready = ready && putQueryParametersReady(query)

        return ready
    }

    private fun putPathParametersReady(path: String): Boolean {
        val split = path
            .substring(1)
            .split("/")

        val indexed = pathParameterized.mapIndexed { i, pair -> i to pair }
        val paramIndexed = indexed.filter { it.second.second != null }
        val pathIndexed = indexed.filter { it.second.second == null }

        return paramIndexed.all {
            val index = it.first
            val param = it.second.second
            putParameterReady(param!!, split[index])
        } && pathIndexed.all {
            val index = it.first
            val name = it.second.first
            name == split[index]
        }
    }

    private fun putQueryParametersReady(query: String): Boolean {
        val params: Map<String, String> = query
            .split("&")
            .map { it.split("=", limit = 2) }
            .associate { it[0] to it.getOrElse(1) { "" } }

        queryParameterized
            .filter { it.name in params }
            .forEach { param ->
                putParameterReady(param, params[param.name] ?: "")
            }

        return true
    }

    private fun putParameterReady(parameter: KParameter, value: String): Boolean {
        try {
            when (parameter.type.classifier) {
                String::class -> parametersReady[parameter] = value
                Int::class -> parametersReady[parameter] = value.toInt()
                Double::class -> parametersReady[parameter] = value.toDouble()
                Boolean::class -> parametersReady[parameter] = value.toBoolean()
            }
            return true
        } catch (ex: Exception) {
            return false
        }
    }

    fun executeClear() {
        parametersReady.clear()
    }

    fun execute(): String {
        return convert(function.callBy(parametersReady)).serialize()
    }
}