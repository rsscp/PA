package json.rest

import java.net.InetSocketAddress
import kotlin.reflect.full.*
import com.sun.net.httpserver.HttpServer


class Server(vararg controllersArray: Any) {

    val controllers: MutableList<Any>
    val socket: InetSocketAddress
    val server: HttpServer

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

    fun createContext(server: HttpServer) {
        controllers.forEach {
            val annotation = it::class.findAnnotation<Mapping>()
            val path = "/" + annotation?.path
            server.createContext(path, ControllerHandler(it))
        }
    }

    fun stop() {
        server.stop(0)
    }
}