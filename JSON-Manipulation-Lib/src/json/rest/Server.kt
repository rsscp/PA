package json.rest

import java.net.InetSocketAddress
import kotlin.reflect.full.*
import com.sun.net.httpserver.HttpServer


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
            server.createContext(path, ControllerHandler(it))
        }
    }
}