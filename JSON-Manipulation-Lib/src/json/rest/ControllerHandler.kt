package json.rest

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class ControllerHandler(controller: Any): HttpHandler {

    private val rootPath: String
    private val mappings: List<MappingHandler>

    init {
        if (!controller::class.hasAnnotation<Mapping>())
            throw IllegalArgumentException("Controller must have Mapping and Method annotations")

        val functions = controller::class.members
            .filter {
                it is KFunction<*> &&
                it.hasAnnotation<Method>() &&
                it.hasAnnotation<Mapping>()
            }
            .map { it as KFunction<*> }

        checkMappingValidity(functions)
        checkMappingRepeated(functions)
        functions.forEach { checkParameters(it) }

        rootPath = "/" + controller::class.findAnnotation<Mapping>()?.path
        mappings = createMappings(controller, functions)
    }

    private fun checkMappingValidity(functions: List<KFunction<*>>) {
        functions.forEach {
            val regex = Regex("^(\\/|(\\/(\\w+|\\{[a-zA-Z]+\\w*}))+)\$")
            val path = "/" + it.findAnnotation<Mapping>()?.path.toString()
            if (!regex.matches(path))
                throw IllegalArgumentException("Mapping path \"$path\" is not valid")
        }
    }

    private fun checkMappingRepeated(functions: List<KFunction<*>>) {
        val checked = functions.map { it.findAnnotation<Mapping>()?.path ?: "" }.toSet()
        if (checked.size != functions.size)
            throw IllegalArgumentException("Mappings cannot have repeated paths")
    }

    private fun checkParameters(function: KFunction<*>) {
        function.parameters
            .filter { it.kind == KParameter.Kind.VALUE }
            .forEach {
                if (!(
                    it.hasAnnotation<PathParam>() ||
                    it.hasAnnotation<HeaderParam>()
                )) throw IllegalArgumentException("Parameter ${it.name} must be annotated with PathParam or HeaderParam")
            }
    }

    private fun createMappings(controller: Any, functions: List<KFunction<*>>): List<MappingHandler> {
        return functions
            .map { MappingHandler(
                controller,
                it,
                it.findAnnotation<Method>()?.method ?: HttpMethod.GET,
                rootPath + '/' + it.findAnnotation<Mapping>()?.path.toString()
            ) }
    }

    override fun handle(exchange: HttpExchange) {
        val path = exchange.requestURI.path ?: ""
        val query = exchange.requestURI.query ?: ""
        val method = exchange.requestMethod ?: ""

        mappings.forEach {
            if (it.executeReady(method, path, query)) { //Primeira match
                sendResponse(
                    exchange,
                    200,
                    it.execute()
                )
                return
            }
        }
        sendResponse(
            exchange,
            404,
            "Not Found"
        )
    }

    private fun sendResponse(exchange: HttpExchange, code: Int, body: String) {
        exchange.sendResponseHeaders(code, body.length.toLong())
        val os = exchange.responseBody
        os.write(body.toByteArray())
        os.close()
    }
}