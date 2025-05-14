package json.rest

import java.net.InetSocketAddress
import java.net.URI
import kotlin.reflect.*
import kotlin.reflect.full.*
import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.Request
import java.io.IOException
import java.io.OutputStream

import json.JsonElement
import json.models.JsonNull


class Server(
    vararg controllerClasses: KClass<*>
) {
    val controllers: MutableList<KClass<*>>

    init {
        controllerClasses.forEach {
            if (!it.hasAnnotation<Mapping>())
                throw IllegalArgumentException("Controller must have Mapping annotation")
        }
        controllers = mutableListOf(*controllerClasses)
    }

    fun start() {
        val socket = InetSocketAddress(8080)
        val server = HttpServer.create(socket, 1)

        createContext(server)
    }

    fun createContext(server: HttpServer) {
        controllers.forEach {
            val annotation = it.findAnnotation<Mapping>()
            val path = annotation?.path
            server.createContext(path, Handler(it))
        }
        server.createContext("")
    }

    fun handleRequest(request: Request): JsonElement {
        val path = request.requestURI.path
        println("Received request for path: $path")

        return JsonNull() //TODO DELETE
    }
}