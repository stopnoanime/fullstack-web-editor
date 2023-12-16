import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.appendElement
import kotlinx.dom.appendText
import org.w3c.dom.events.KeyboardEvent
import org.w3c.fetch.RequestInit
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

val baseDomain = "http://localhost:8080/";

suspend fun main() {
    val domElement = document.querySelector(".monaco")!!;
    val editor = createMonacoEditor(domElement);

    editor.setValue("abc");

    document.addEventListener("keydown") { e ->
        val keyEvent = e as KeyboardEvent;
        val ctrlKey = e.ctrlKey || e.metaKey;

        if (ctrlKey && keyEvent.key == "s") {
            e.preventDefault()
            document.body?.appendText(editor.getValue());
        } else if(ctrlKey && keyEvent.key == "o") {
            e.preventDefault()

            //listTextFiles()

            editor.setValue("open file");
        }
    }

    val client = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }
    val response: HttpResponse = client.get(baseDomain + "listTextFiles")
    println(response.status)
    client.close()
}

suspend fun listTextFiles() {
    val client = HttpClient()
    val response: HttpResponse = client.get(baseDomain + "listTextFiles")

}