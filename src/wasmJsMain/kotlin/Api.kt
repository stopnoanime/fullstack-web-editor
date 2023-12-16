import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.call.*
import com.common.TextFile
import io.ktor.http.*

class Api {
    private val apiDomain = "http://localhost:8081"
    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getTextFiles(): List<TextFile> {
        return client.get("$apiDomain/getTextFiles").body()
    }

    suspend fun saveTextFile(textFile: TextFile) {
        client.put("$apiDomain/saveTextFile") {
            contentType(ContentType.Application.Json)
            setBody(textFile)
        }
    }
}

