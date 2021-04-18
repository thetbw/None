package xyz.thetbw

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import xyz.thetbw.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureTemplating()
    }.start(wait = true)
}
