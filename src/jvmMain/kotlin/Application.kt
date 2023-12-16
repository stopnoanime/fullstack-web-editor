import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8081) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            anyHost()
        }
        routing {
            TextFileRouting()
        }
    }.start(wait = true)
}
