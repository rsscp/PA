package json.rest

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class Handler(controller: Any): HttpHandler {

    val rootPath: String
    val mappings: List<KFunction<*>>

    init {
        if (!controller::class.hasAnnotation<Mapping>())
            throw IllegalArgumentException("Controller must have Mapping and Method annotations")

        rootPath = "/" + controller::class.findAnnotation<Mapping>()?.path
        mappings = controller::class.members
            .filter {
                it.hasAnnotation<Mapping>() &&
                it.hasAnnotation<Method>() &&
                it.findAnnotation<Mapping>()?.path != ""
            }.map { it as KFunction<*> }

        mappings.forEach { checkParameters(it) }
    }

    override fun handle(exchange: HttpExchange?) {
        require(exchange != null)
        val method = exchange.requestMethod?.toString()
        val path = exchange.requestURI.path
        val pathPartsRequest = path
            .substring(1)
            .split("/")

        val matches = mutableListOf<MutableMap<String, Any>>()//TODO<<<<<<<<<<<<<<<<<<<<<<<<<<
        mappings.forEach {
            val specificPath = it.findAnnotation<Mapping>()?.path ?: ""
            val pathPartsAnnotation = (rootPath + specificPath)
                .substring(1)
                .split("/")

            val pathParameters = match(
                pathPartsRequest,
                pathPartsAnnotation
            )

            pathParameters?.mapValues { TODO("Implement") }
            pathParameters ?: matches.add(pathParameters)
        }

        println("Received request for path: $path with method: $method")
    }

    private fun match(pathRequest: List<String>, pathAnnotation: List<String>): MutableMap<String, String>? {

        val pathParameters = mutableMapOf<String, String>()
        var matching = true

        if (pathRequest.size != pathAnnotation.size)
            pathRequest.forEachIndexed { i, part ->
                val regex = Regex("\\{(\\w+)}")
                val name = regex.find(pathAnnotation[i])?.value

                matching = matching && (name != null || part == pathAnnotation[i])
                pathParameters[name ?: "\\"] = part
            }
        else
            matching = false

        if (matching)
            return pathParameters
        return null
    }

    private fun checkParameters(mapping: KFunction<*>) {
        TODO("Implement")
    }

    private fun sendResponse(exchange: HttpExchange, code: Int, body: String) {
        exchange.sendResponseHeaders(code, body.length.toLong())
        val os = exchange.responseBody
        os.write(body.toByteArray())
        os.close()
    }
}