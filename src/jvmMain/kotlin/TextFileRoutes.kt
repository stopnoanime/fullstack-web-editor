import com.common.TextFile
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val textFileStorage = mutableListOf<TextFile>()

fun Route.TextFileRouting() {
    get("/listTextFiles") {
        call.respond(textFileStorage.map { it.id })
    }

    get("/getTextFile/{id}") {
        val id = call.parameters["id"]
        val textFile = textFileStorage.find { it.id == id }

        if(textFile == null) {
            call.respondText(
                "No textFile found with id $id",
                status = HttpStatusCode.NotFound
            )
        } else {
            call.respond(textFile)
        }
    }

    put("/saveTextFile") {
        val textFile = call.receive<TextFile>()

        val oldTextFile = textFileStorage.find { it.id == textFile.id }

        if(oldTextFile == null) {
            textFileStorage.add(textFile)
        } else {
            oldTextFile.content = textFile.content
        }

        call.response.status(HttpStatusCode.OK)
    }
}