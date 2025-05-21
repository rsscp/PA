package json.rest

import java.net.InetSocketAddress
import kotlin.reflect.full.*
import com.sun.net.httpserver.HttpServer


class Server(vararg controllersArray: Any) {

    private val controllers: MutableList<Any>
    private val socket: InetSocketAddress
    private val server: HttpServer

    init {
        controllersArray.forEach {
            if (!it::class.hasAnnotation<Mapping>())
                throw IllegalArgumentException("Controller must have Mapping annotation")
        }
        controllers = mutableListOf(*controllersArray)
        socket = InetSocketAddress(8080)
        server = HttpServer.create(socket, 1)
        createContext(server)
    }

    fun start() {
        server.start()
    }

    fun stop() {
        server.stop(0)
    }

    private fun createContext(server: HttpServer) {
        checkRepeated()
        controllers.forEach {
            val annotation = it::class.findAnnotation<Mapping>()
            val path = "/" + annotation?.path
            server.createContext(path, ControllerHandler(it))
        }
    }

    private fun checkRepeated() {
        val checked = controllers.map { it::class.findAnnotation<Mapping>()?.path ?: "" }.toSet()
        if (checked.size != controllers.size)
            throw IllegalArgumentException("Root mappings cannot have repeated paths")
    }
}