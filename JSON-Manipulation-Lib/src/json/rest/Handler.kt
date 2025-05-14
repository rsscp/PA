package json.rest

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class Handler(controller: Any): HttpHandler {

    val mappings: List<KFunction<*>>

    init {
        if (!controller::class.hasAnnotation<Mapping>())
            throw IllegalArgumentException("Controller must have Mapping annotation")

        mappings = controller::class.members
            .filter { it.hasAnnotation<Mapping>() }
            .map { it as KFunction<*> }
    }

    override fun handle(exchange: HttpExchange?) {
        TODO("Not yet implemented")
    }

}