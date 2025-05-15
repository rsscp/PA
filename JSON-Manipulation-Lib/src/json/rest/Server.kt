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

import json.models.JsonElement
import json.models.JsonNull


class Server(vararg controllersArray: Any) {

    val controllers: MutableList<Any>

    init {
        controllersArray.forEach {
            if (!it::class.hasAnnotation<Mapping>())
                throw IllegalArgumentException("Controller must have Mapping annotation")
        }
        controllers = mutableListOf(*controllersArray)
    }

    fun start() {
        val socket = InetSocketAddress(8080)
        val server = HttpServer.create(socket, 1)
        createContext(server)
        server.start()
    }

    fun createContext(server: HttpServer) {
        controllers.forEach {
            val annotation = it::class.findAnnotation<Mapping>()
            val path = "/" + annotation?.path
            println("Mapping $path")
            server.createContext(path, Handler(it))
        }
    }
}